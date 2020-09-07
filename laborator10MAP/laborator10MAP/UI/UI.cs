using laborator10MAP.domain;
using laborator10MAP.service;
using laborator10MAP.validator;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace laborator10MAP.ui
{
    public class Ui
    {

        private Service<int, Jucator> serviceJucator;
        private readonly Service<int, Echipa> serviceEchipa;
        private MeciService MeciService;
        private JucatorActivService JucatorActivService;
        private List<string> Echipe;
        private List<string> Scoli;

        public Ui(Service<int, Jucator> serviceJucator, Service<int, Echipa> serviceEchipa, MeciService meciService, JucatorActivService jucatorActivService, List<string> echipe, List<string> scoli)
        {
            this.serviceJucator = serviceJucator;
            this.serviceEchipa = serviceEchipa;
            MeciService = meciService;
            JucatorActivService = jucatorActivService;
            Echipe = echipe;
            Scoli = scoli;
        }




        private string ReadString(string text)
        {
            Console.WriteLine(text);
            return Console.ReadLine();
        }

        private int ReadInt(string text)
        {
            Console.WriteLine(text);
            return int.Parse(Console.ReadLine());
        }

        private void meniuAfisare()
        {
            Console.WriteLine("0 - Adauga Echipa\n" +
                              "1 - Adauga Jucator\n" +
                              "2 - Adauga Meci\n" +
                              "3 - Adauga JucatorActiv\n" +
                              "4 - Toti jucatorii unei echipe date" +
                              "5 - Toti jucatorii activi ai unei echipe de la un anumit meci\n" +
                              "6 - Toate meciurile dintr-o anumita perioada calendaristica\n" +
                              "7 - Scorul de la un anumit meci\n" +
                              "8 - Iesire\n" +
                              "9 - Afiseaza toate meciurile \n"+
                              "10 - Jucatori activi\n"

                );
        }

        public void rulare()
        {
            bool rulare = true;
            meniuAfisare();
            while (rulare)
            {
                try
                {
                    switch (ReadString("Introdu o comanda:"))
                    {
                        case "0":
                            serviceEchipa.Save(new Echipa(
                                ReadInt("id echipa:\n"),
                                ReadString("nume echipa:\n")
                            ));
                            break;
                        case "1":
                            serviceJucator.Save(new Jucator(
                            ReadInt("id jucator:\n"),
                            ReadString("nume jucator:\n"),
                            ReadString("scoala jucator:\n"),
                            serviceEchipa.FindOne(ReadInt("echipa id jucator:\n"))
                                ));
                            break;
                        case "2":
                            MeciService.Save(new Meci(
                                ReadInt("id meci:\n"),
                                serviceEchipa.FindOne(ReadInt("id echipa 1:\n")),
                                serviceEchipa.FindOne(ReadInt("id echipa 2:\n")),
                                DateTime.Parse(ReadString("introdu o data. formatul e dd/mm/yyyy:\n"))
                                ));
                            break;
                        case "3":
                            JucatorActivService.Save(new JucatorActiv(
                                ReadInt("id jucator:\n"),
                                ReadString("nume jucator:\n"),
                                ReadString("scoala jucator:\n"),
                                serviceEchipa.FindOne(ReadInt("echipa id jucator:\n")),
                                ReadInt("id Meci:\n"),
                                ReadInt("nr puncte inscrise:\n"),
                                (Tip)Enum.Parse(typeof(Tip), ReadString("tip jucator (Rezerva/Participant):"))
                            ));

                            break;
                        case "4"://toti jucatorii unei echipe date
                            int echipaId = ReadInt("echipa id:\n");
                            foreach (var VARIABLE in this.serviceJucator.FindAll())
                            {
                                if (VARIABLE.Echipa.Id.Equals(echipaId))
                                    Console.WriteLine(VARIABLE);
                            }
                            break;
                        case "5":// Toti jucatorii activi ai unei echipe de la un anumit meci
                            foreach (var VARIABLE in this.JucatorActivService.TotiJucatoriiActiviDeLaOEchipaDeLaUnMeci(
                                ReadInt("id echipa:\n"), ReadInt("id meci:\n")))
                            {
                                Console.WriteLine(VARIABLE);
                            }
                            break;
                        case "6"://Toate meciurile dintr-o anumita perioada calendaristica
                            foreach (var VARIABLE in MeciService.ToateMeciurileDintr_oPerioada(
                            DateTime.Parse(ReadString("introdu data de inceput. formatul e dd/mm/yyyy:\n")),
                            DateTime.Parse(ReadString("introdu data de sfarsit. formatul e dd/mm/yyyy:\n"))))
                            {
                                Console.WriteLine(VARIABLE);
                            }
                            break;
                        case "7"://Scorul de la un anumit meci
                            int idMeci = ReadInt("id meci:");
                            List<JucatorActiv> list = new List<JucatorActiv>();
                            Meci meci = this.MeciService.FindOne(idMeci);
                            Console.Write(meci.Echipa1.Nume + " ");
                            Console.Write(this.JucatorActivService.FindAll().Where(x => x.Echipa.Id.Equals(meci.Echipa1.Id)).Sum(x => x.NrPuncteInscrise) + " - ");
                            Console.Write(this.JucatorActivService.FindAll().Where(x => x.Echipa.Id.Equals(meci.Echipa2.Id)).Sum(x => x.NrPuncteInscrise) + " ");
                            Console.Write(meci.Echipa2.Nume + "\n");
                            break;
                        case "8":
                            rulare = false;
                            break;
                        case "9":
                            foreach (var VARIABLE in this.MeciService.FindAll())
                            {
                                Console.WriteLine(VARIABLE);
                            }
                            break;
                        case "10":
                            foreach (var VARIABLE in this.JucatorActivService.FindAll())
                            {
                                Console.WriteLine(VARIABLE);
                            }
                            break;
                        default:
                            meniuAfisare();
                            break;
                    }
                }
                catch (Exception e)
                {
                    if (e is ValidationException || e is FormatException)
                        Console.WriteLine(e.Message);
                    else
                        throw;
                }
            }
        }

    }
}

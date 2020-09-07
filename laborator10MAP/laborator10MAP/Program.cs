
using laborator10MAP.domain;
using laborator10MAP.service;
using laborator10MAP.validator;
using laborator10MAP.repository;
using System;
using System.Collections.Generic;
using laborator10MAP.Repository;
using laborator10MAP.ui;

namespace laborator10MAP
{
    internal class Program
    {
        private static List<string> echipe = new List<string>(new[]
        {
            "Houston Rockets",
            "Los Angeles Lakers",
            "LA Clippers",
            "Chicago Bulls",
            "Cleveland Cavaliers",
            "Utah Jazz",
            "Brooklyn Nets",
            "New Orleans Pelicans",
            "Indiana Pacers",
            "Toronto Raptors",
            "Charlotte Hornets",
            "Phoenix Suns",
            "Portland TrailBlazers",
            "Golden State Warriors",
            "Washington Wizards",
            "San Antonio Spurs",
            "Orlando Magic",
            "Denver Nuggets",
            "Detroit Pistons",
            "Atlanta Hawks",
            "Dallas Mavericks",
            "Sacramento Kings",
            "Oklahoma City Thunder",
            "Boston Celtics",
            "New York Knicks",
            "Minnesota Timberwolves",
            "Miami Heat",
            "Milwaukee Bucks"
        }); //28

        private static List<string> scoli = new List<string>(new[]
        {
            "Scoala Gimnaziala “Horea” ",
            "Scoala Gimnaziala Octavian Goga",
            "Liceul Teoretic Lucian Blaga",
            "Scoala Gimnaziala Ioan Bob",
            "Scoala Gimnaziala Ion Creanga",
            "Colegiul National Pedagogic Gheorghe Lazar",
            "Scoala Gimnaziala Internationala SPECTRUM",
            "Colegiul National Emil Racovita",
            "Colegiul National George Cosbuc",
            "Scoala Gimnaziala Ion Agarbiceanu",
            "Liceul Teoretic Avram Iancu",
            "Scoala Gimnaziala Constantin Brancusi",
            "Liceul Teoretic Onisifor Ghibu",
            "Liceul Teoretic Onisifor Ghibu",
            "Liceul cu Program Sportiv Cluj-Napoca",
            "Liceul Teoretic Nicolae Balcescu",
            "Liceul Teoretic Gheorghe Sincai",
            "Scoala Nicolae Titulescu",
            "Scoala Gimnaziala Liviu Rebreanu",
            "Scoala Gimnaziala Iuliu Hatieganu",
            "Liceul Teoretic Bathory Istvan",
            "Colegiul National George Baritiu",
            "Liceul Teoretic Apaczai Csere Janos",
            "Seminarul Teologic Ortodox",
            "Liceul de Informatica Tiberiu Popoviciu",
            "Scoala Gimnaziala Alexandru Vaida – Voevod",
            "Liceul Teoretic ELF",
            "Scoala Gimnaziala Gheorghe Sincai Floresti"
        }); //28

       

        public static void Main()

        {

            string[] nume =
         {
                "jucator1",
                "jucator",
                "jucator3",
                "jucator4",
                "jucator5",
                "jucator6",
                "jucator7",
                "jucator8",
                "jucator9",
                "jucator10",
                "jucator11",
                "jucator12",
                "jucator13",
                "jucator14",
                "jucator15",
                "jucator16",
                "jucator17",
                "jucator18",
                "jucator19",
                "jucator20",
                "jucator21",
                "jucator22",
                "jucator23",
                "jucator24",
                "jucator25",
                "jucator26",
                "jucator27",
                "jucator28",

            };


            EchipaFileRepository EchipaFileRepository =
                new EchipaFileRepository(new EchipaValidator(),"echipe.txt");
            Service<int, Echipa> EchipaService = new Service<int, Echipa>(EchipaFileRepository);

            JucatorFileRepository jucatorFileRepository = new JucatorFileRepository(new JucatorValidator(),
                 "jucatori.txt", EchipaFileRepository);
            Service<int, Jucator> JucatorService = new Service<int, Jucator>(jucatorFileRepository);

            MeciFileRepository MeciFileRepository =
                new MeciFileRepository(new MeciValidator(), "meciuri.txt", EchipaFileRepository);
            MeciService meciService = new MeciService(MeciFileRepository);

            JucatorActivFileRepository JucatorActivFileRepository =
                new JucatorActivFileRepository(new JucatorActivValidator(), "jucatoriActivi.txt",
                    EchipaFileRepository);
            JucatorActivService JucatorActivService =
                new JucatorActivService(JucatorActivFileRepository);

          
           


            //echipe
            int id = 0;
            foreach (string echipa in echipe) EchipaFileRepository.Save(new Echipa(id++, echipa));

            //jucatori
            for (id = 0; id < 28; id++)
            {
                jucatorFileRepository.Save(new Jucator(id, nume[id], scoli[id/2], EchipaFileRepository.FindOne(id)
                    ));

            }
            //meciuri
            for (id = 0; id < 28; id++)
            {
                MeciFileRepository.Save(new Meci(id, EchipaFileRepository.FindOne(id),
                    EchipaFileRepository.FindOne(id/2), DateTime.Now));
            }
            Random rnd = new Random();
            //jucatori activi
            foreach (var meci in MeciFileRepository.FindAll())
            {
                foreach (var jucator in jucatorFileRepository.FindAll())
                {
                    if (jucator.Echipa.Id.Equals(meci.Echipa1.Id))//am gasit un jucator ce e la echipa 1 si il adaugam la activi
                    {
                        JucatorActivFileRepository.Save(new JucatorActiv(jucator.Id, jucator.Nume, jucator.Scoala,
                            jucator.Echipa, meci.Id, rnd.Next(1,5), Tip.PARTICIPANT));
                    }
                    if (jucator.Echipa.Id.Equals(meci.Echipa2.Id))//am gasit un jucator ce e la echipa 2 si il adaugam la activi
                    {
                        JucatorActivFileRepository.Save(new JucatorActiv(jucator.Id, jucator.Nume, jucator.Scoala,
                            jucator.Echipa, meci.Id, rnd.Next(2,7), Tip.PARTICIPANT));
                    }
                }
            }

            Ui ui = new Ui(JucatorService, EchipaService, meciService, JucatorActivService,
                echipe, scoli);

            ui.rulare();
        }

    }
}

package Repository;

import Domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLStudentRepository extends InMemoryRepository<String, Student> {
    private String fileName;

    public XMLStudentRepository( String fileName) {
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);

            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            for(int i=0; i < children.getLength(); i++) {
                Node studentElement = children.item(i);
                if(studentElement instanceof Element) {
                    Student student = createStudentFromElement((Element)studentElement);
                    super.save(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(){
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root  = document.createElement("stud");
            document.appendChild(root);
            super.findAll().forEach(m->{
                Element e = createElementfromStudent(document,m);
                root.appendChild(e);
            });

            //write Document to file
            Transformer transformer = TransformerFactory.
                    newInstance().newTransformer();
            transformer.transform(new DOMSource(document),
                    new StreamResult(fileName));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Element createElementfromStudent(Document document, Student s) {
        Element e = document.createElement("student");
        e.setAttribute("studentid", s.getId());

        Element nume = document.createElement("nume");
        nume.setTextContent(s.getNume());
        e.appendChild(nume);

        Element prenume = document.createElement("prenume");
        prenume.setTextContent(s.getPrenume());
        e.appendChild(prenume);

        Element grupa = document.createElement("grupa");
        grupa.setTextContent(s.getGrupa());
        e.appendChild(grupa);

        Element email = document.createElement("email");
        email.setTextContent(s.getEmail());
        e.appendChild(email);

        Element cadruDidacticIndrumatorLab = document.createElement("cadruDidacticIndrumatorLab");
        cadruDidacticIndrumatorLab.setTextContent(s.getCadruDidacticIndrumatorLab());
        e.appendChild(cadruDidacticIndrumatorLab);

        return e;
    }

    @Override
    public Student save(Student entity) {
        Student stuff;
        stuff = super.save(entity);
        if (stuff == null){
            writeToFile();
        }
        return stuff;
    }

    private Student createStudentFromElement(Element messageTaskElement) {
        String studentId = messageTaskElement.getAttribute("studentid");
        NodeList nods = messageTaskElement.getChildNodes();
       String nume =messageTaskElement.getElementsByTagName("nume")
               .item(0)
               .getTextContent();

        String prenume =messageTaskElement.getElementsByTagName("prenume")
                .item(0)
                .getTextContent();

        String grupa =messageTaskElement.getElementsByTagName("grupa")
                .item(0)
                .getTextContent();

        String email =messageTaskElement.getElementsByTagName("email")
                .item(0)
                .getTextContent();

        String cadruDidacticIndrumatorLab =messageTaskElement.getElementsByTagName("cadruDidacticIndrumatorLab")
                .item(0)
                .getTextContent();
        return new Student(studentId,nume,prenume,grupa,email,cadruDidacticIndrumatorLab);

    }


}

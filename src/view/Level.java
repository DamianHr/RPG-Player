package view;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sun.awt.AWTAutoShutdown;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * [19:36:36] Damian: <?xml version="1.0"?>
 <game xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:noNamespaceSchemaLocation="../../schemas/gameFile.xsd">

 <title>my first game</title>
 <situation code="0">
    <exposition>You're in some place somewhere</exposition>
    <question label="You don't look around, cause you're blind as fuck.">
         <answerpool>
            <answer  value="0" goTo="0">You decide to stay here</answer>
            <answer  value="-1" goTo="1">You take one step ahead</answer>
            <answer  value="0" goTo="2">You extend your arms and search for a wall</answer>
        </answerpool>
    </question>
 </situation>

 <situation code="1">
    <exposition>You bat-eyed basterd has make a bad decision, you happened to be on the edge of a cliff. Poor thing, you're dead</exposition>
 </situation>

 <situation code="2">
    <exposition>There's... Nothing, at least not within arm reached. Or is there ? Do you have arms in the first place ?</exposition>
    <question label="Damn it ! Do you have arms ? DO YOU ?">
        <answerpool>
            <answer  value="0" goTo="1">You don't know, and it scares you. Really does... you're going full retard and start running around.</answer>
            <answer  value="0" goTo="0">Mah ? Why would you care ?</answer>
        </answerpool>
    </question>
 </situation>
 </game>
 */
public class Level {
    //public List<Level> access = new ArrayList<Level>();
    public List<Entrance> entrances = new ArrayList<Entrance>();

    public int id;
    public String exposition;
    public String question;

    private static String SITUATION = "situation", ANSWER = "answer", CODE = "code", ANSWERPOOL = "answerpool", VALUE = "value", GOTO = "goTo", QUESTION = "question", LABEL = "label", EXPOSITION = "exposition";

    public Level(List<Entrance> entrances, int id, String exposition, String question) {
        this.entrances = entrances;
        this.id = id;
        this.exposition = exposition;
        this.question = question;
    }



    public Level(String xmlString) {

        EntranceLevelManager entranceLevelManager = new EntranceLevelManager();


        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder= docFactory.newDocumentBuilder();

            InputStream stream = new ByteArrayInputStream(xmlString.getBytes());
            Document document = documentBuilder.parse(stream);

            Element root = document.getDocumentElement();

            NodeList situations = root.getElementsByTagName(SITUATION);
            
            for(int i = 0; i < situations.getLength(); ++i){
                Element situation = (Element)situations.item(i);

                int code = Integer.parseInt(situation.getAttribute(CODE));
                String exposition = situation.getElementsByTagName(EXPOSITION).item(0).getTextContent();
                String question = "";

                NodeList questions = situation.getElementsByTagName(QUESTION);
                Element questionElement;

                ArrayList<Entrance> entrances = new ArrayList<Entrance>();
                //get question label + create hypothetical entrances
                if(0 < questions.getLength() && null != (questionElement = (Element) questions.item(i))){
                    question = questionElement.getAttribute(LABEL);
                    NodeList answerpoolList = questionElement.getElementsByTagName(ANSWERPOOL);
                    Element answerpool;

                    //answers -> entrances

                    if(0 < answerpoolList.getLength() && null != (answerpool = (Element) answerpoolList.item(0))){
                        Node answer = answerpool.getFirstChild();

                        while(null != answer){
                            if(ANSWER.equals(answer.getNodeName())){
                                Element answerElement = (Element) answer;

                                int value = Integer.parseInt(answerElement.getAttribute(VALUE));
                                int goTo =  Integer.parseInt(answerElement.getAttribute(GOTO));
                                String text = answerElement.getTextContent();

                                Entrance entrance = new Entrance(text, value);
                                entrances.add(entrance);

                                entranceLevelManager.linkToLevel(entrance, goTo);
                            }

                            answer = answer.getNextSibling();
                        }
                    }
                }


                //if code == 0 that's the first level -> it's this
                if(0 == code){
                    this.exposition = exposition;
                    this.entrances = entrances;
                    this.id = 0;
                    this.question = question;

                    entranceLevelManager.addLevel(this);
                }
                //else it's another level, we make it and add it to the rest
                else{
                    Level level = new Level(entrances,code, exposition, question);
                    entranceLevelManager.addLevel(level);
                }

            }

        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
        }catch(SAXException se){
            se.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}






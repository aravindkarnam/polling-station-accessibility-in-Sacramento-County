package net.tekpartner.hack4sac.voterregistration;

import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.util.*;

/**
 * Created by DELL on 3/31/2016.
 */
public class StaticHtmlPages {
    private static final Logger logger = Logger.getLogger(StaticHtmlPages.class.getName());

    public StaticHtmlPages() {
    }

    public void createHtmlPage(Map<String, PollingStation> graphData) {
        Iterator<String> ppid = graphData.keySet().iterator();
       /* while (ppid.hasNext()) {
            String poll_id = ppid.next();
            PollingStation p = graphData.get(poll_id);
            p.print();
        }*/
        //StringBuffer buf=new StringBuffer();
        StringBuffer address = new StringBuffer();
        String pollingPlaceName = "";
        StringBuffer mapCo = new StringBuffer();
        String fileName = "";
        String cat = "";
        String allSub = "";
        StringBuffer appnd = new StringBuffer();
        StringBuffer mainData = new StringBuffer();
        HashMap<String, Integer> allSubCat = new HashMap<String, Integer>();
        int temp = 0;
        while (ppid.hasNext()) {
            String poll_id = ppid.next();
            PollingStation p = graphData.get(poll_id);
            Iterator<String> section = (Iterator<String>) p.getSections();
            while (section.hasNext()) {
                String s = Utility.trimmer(section.next());
                double d = Double.parseDouble(s);
                int i = (int) d;
                allSubCat.put(s, i);
            }
            String requireMapping = getRequireMapping(allSubCat);
            //logger.debug("value is:"+requireMapping);

            section = (Iterator<String>) p.getSections();
            while (section.hasNext()) {
                String test = Utility.trimmer(section.next());
                //logger.debug(test);

                Iterator<String> category = (Iterator<String>) p.getCategories(test);
                while (category.hasNext()) {
                    String thisCategory = category.next();
                    //logger.debug("Category : " + thisCategory);
                    Iterator<String> subcategory = (Iterator<String>) p.getSubCategories(Utility.trimmer(test), Utility.trimmer(thisCategory));
                    while (subcategory.hasNext()) {
                        String thisSubcategory = subcategory.next();
                        //logger.debug("thisSubcategory : " + thisSubcategory);
                        Iterator<Question> question = (Iterator<Question>) p.getQuestions(Utility.trimmer(test), Utility.trimmer(thisCategory), Utility.trimmer(thisSubcategory));

                        while (question.hasNext()) {
                            Question thisQuestion = question.next();
                            if (test.toString().equals("0")) {
                                //Name of polling station
                                if (thisQuestion.getQuestion().equals("Polling Place address")) {
                                    address.append(thisQuestion.getAnswer());
                                }
                                if (thisQuestion.getQuestion().equals("City")) {
                                    address.append(thisQuestion.getAnswer());
                                }
                                if (thisQuestion.getQuestion().equals("Polling Place name")) {
                                    pollingPlaceName = thisQuestion.getAnswer();
                                }
                                if (thisQuestion.getQuestion().equals("Latitude")) {
                                    mapCo.append(thisQuestion.getAnswer() + ",");
                                }
                                if (thisQuestion.getQuestion().equals("Longitude")) {
                                    mapCo.append(thisQuestion.getAnswer());
                                }
                                //Address
                                //Map
                            } else {
                               /* logger.debug(requireMapping);
                                String[] strMap=requireMapping.split("#");
                                for(int y=0;y<strMap.length;y++){
                                //logger.debug(strMap[y]);
                                   /* String[] eachSection=strMap[y].split(",");
                                    for(int u=0;u<eachSection.length;u++){
                                        logger.debug(eachSection[y]);
                                    }
                                 logger.debug("--------------------------------------------------------------------------");
                                }*/
                                // Code to create dynamic table rows

                                String strv = "  <tr>" +
                                        "		<td>" + thisCategory + "</td>" +
                                        "		<td>" + thisSubcategory + "</th>" +
                                        "		<td>" + thisQuestion.getQid() + "</td>" +
                                        "		<td>" + thisQuestion.getQuestion() + "</td>" +
                                        "		<td>" + thisQuestion.getAnswer() + "</td>" +
                                        "		<td>" + thisQuestion.getData() + "</td>" +
                                        "		<td>" + thisQuestion.getComments() + "</td>	" +
                                        "      </tr>" +
                                        "	   <tr>";
                                mainData.append(strv);

                                logger.debug("::::::::::::::::::::::::::::::::::::::::::");
                                logger.debug("Subcategory Id :" + test);
                                logger.debug("Category : " + thisCategory);
                                logger.debug("thisSubcategory : " + thisSubcategory);
                                logger.debug("Questions : " + thisQuestion.getQuestion());
                                logger.debug("Answer : " + thisQuestion.getAnswer());
                                logger.debug("Data : " + thisQuestion.getData());
                                logger.debug("Comments : " + thisQuestion.getComments());
                                logger.debug("::::::::::::::::::::::::::::::::::::::::::");
                            }
                        }
                    }

                }
                if (!test.toString().equals("0")) {
                    writeHtmlFile(test, getStaticPartOfHtmlPage(test.toString(), address.toString(), mapCo.toString()) + mainData.toString() + getHtmlFooter());
                }
                //logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
        }
    }

    private String getRequireMapping(HashMap<String, Integer> allSubCat) {
        Set<Integer> h = new HashSet<Integer>();
        for (Map.Entry<String, Integer> entry : allSubCat.entrySet()) {
            String key = entry.getKey().toString();
            Integer value = entry.getValue();
            h.add(value);
            // logger.debug("key, " + key + " value " + value);
        }
        Iterator i = h.iterator();
        StringBuffer strBf = new StringBuffer();
        while (i.hasNext()) {
            Integer name = (Integer) i.next();
            // logger.debug("All Key Of :"+name);
            for (Map.Entry<String, Integer> entry : allSubCat.entrySet()) {
                if (entry.getValue() == name) {
                    //  logger.debug(entry.getKey());
                    //entry.
                    strBf.append(entry.getKey() + ",");
                }
            }
            strBf.append("#");
        }
        //logger.debug(strBf.toString());
        return strBf.toString();
    }

    private String getHtmlFooter() {
        return "</table>" + "</body>" + "</html>";

    }

    private String getDynamicCode(String str) {
        return "";
    }

    private String getStaticPartOfHtmlPage(String pollingPlaceName, String address, String mapCo) {
        String htmlHeader =
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +

                        "<style>" +
                        "table, th, td {" +
                        "    border: 1px solid black;" +
                        "    border-collapse: collapse;" +
                        "}" +
                        "th, td {" +
                        "	padding: 5px;" +
                        "    text-align: center;" +
                        "}" +

                        "</style>" +

                        "</head>";
        String htmlBody =
                "<body>" +

                        "<table border=\"1\" style=\"width:100%\">" +
                        "  <tr>" +
                        "    <td align=\"center\" height=\"70px\">" + pollingPlaceName + "</td>" +
                        "   </tr>" +
                        "</table>" +
                        "<br/> " +
                        "<table border=\"1\" style=\"width:100%\">" +
                        "  <tr style=\"\">" +
                        "    <td align=\"center\" height=\"70px\">Images Gallary</td>" +
                        "  </tr>" +
                        "</table>" +
                        "<br/> " +
                        "<table  border=\"1\" style=\"width:100%\">" +
                        "  <tr>" +
                        "    <td align=\"center\" height=\"70px\" width=\"50%\">" + address + "</td>" +
                        "	<td align=\"center\" height=\"70px\" width=\"50%\">" + mapCo + "</td>" +
                        "  </tr>" +
                        "</table>" +
                        "<br/>" +

                        "<table  border=\"1\" style=\"width:100%\">" +

                        "      <tr>" +
                        "        <th>Category</th>" +
                        "		<th>Subcategory</th>" +
                        "		<th>Question Id</th>" +
                        "		<th>Question</th>" +
                        "		<th>Answer</th>" +
                        "		<th>Data</th>" +
                        "		<th>Comments</th>		" +
                        "      </tr>";
        return htmlHeader + htmlBody;
    }

    private void writeHtmlFile(String fileName, String htmlText) {
        try {
            PrintWriter writer = new PrintWriter("E:\\CSUS_Sacro\\CSUS Book Store\\Hackthon Code\\File_Create\\" + fileName + ".html", "UTF-8");
            writer.println(htmlText);
            writer.close();
        } catch (Exception e) {
            logger.debug("Got Exception during html file creation:" + e);
        }
    }

    /*public String getHtmlDynamicPage(String[] lines) {
        StringBuffer strBuf = new StringBuffer();
        String catId = "";
        int flag = 0;
        int maintainRowSpan = 0;
        for (int k = 0; k < lines.length; k++) {
            String line = lines[k];

            logger.debug(line);
            String[] strArryValue = line.split(",");

            catId = strArryValue[1];
            if (Integer.parseInt(catId.replace("\"", "")) > 0) {
                String temp = "";
                if (!temp.equals(catId)) {
                    temp = catId;
                    flag = 1;
                    maintainRowSpan++;
                } else {
                    flag = 0;
                }
                String strv = "";
                if (k == 0) {
                    strv = "      <tr>" +
                            "       <td rowspan=\"" + ((lines.length * 2 * maintainRowSpan) + 1) + "\">" + strArryValue[2] + "</td> " +
                            "		<td rowspan=\"" + ((lines.length * 2) + 1) + "\">" + strArryValue[3] + "</td>" +
                            "		<td>" + strArryValue[4] + "</td>" +
                            "		<td>" + strArryValue[5] + "</th>" +
                            "		<td>" + strArryValue[6] + "</td>" +
                            "		<td>" + strArryValue[7] + "</td>" +
                            "		<td>" + strArryValue[8] + "</td>	" +
                            "      </tr>" +
                            "	   <tr>";
                } else {
                    strv = " <tr>" +

                            "		<td>" + strArryValue[4] + "</td>" +
                            "		<td>" + strArryValue[5] + "</th>" +
                            "		<td>" + strArryValue[6] + "</td>" +
                            "		<td>" + strArryValue[7] + "</td>" +
                            "		<td>" + strArryValue[8] + "</td>	" +
                            "      </tr>" +
                            "	   <tr>";
                }

                strBuf.append(strv);
            }

        }
        return "";
    }*/
    private String getDynamicPage(String strValue) {
        String strv = "  <tr>" +
                "		<td>" + "Value" + "</td>" +
                "		<td>" + "Value" + "</th>" +
                "		<td>" + "Value" + "</td>" +
                "		<td>" + "Value" + "</th>" +
                "		<td>" + "Value" + "</td>" +
                "		<td>" + "Value" + "</td>" +
                "		<td>" + "Value" + "</td>	" +
                "      </tr>" +
                "	   <tr>";
        return strv;
    }

    public static void main(String[] args) {
        StaticHtmlPages staticdata = new StaticHtmlPages();
//        staticdata.readValue();
//        staticdata.createHtmlPage();
    }
}
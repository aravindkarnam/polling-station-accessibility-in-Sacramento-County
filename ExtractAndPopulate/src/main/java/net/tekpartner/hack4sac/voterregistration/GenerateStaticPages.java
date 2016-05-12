package net.tekpartner.hack4sac.voterregistration;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.io.PrintWriter;

/**
 * Created by DELL on 3/26/2016.
 */
public class GenerateStaticPages {
    private static final Logger logger = Logger.getLogger(GenerateStaticPages.class.getName());

    public int getSubCatChange(String oldValue, String newValue) {
        int flag = 0;
        if (oldValue.toString().trim().equals(newValue.toString().trim())) {
            flag = 1;
        }
        return flag;
    }

    public void getData() {
        try {
            int count = 0;
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet("http://saccounty.cloudapi.junar.com/api/v2/datastreams/VRE-DENOR-FULL-LIST/data.csv/?auth_key=0b0d5b573ac8556dd7dcf7adde76d3b53217668a&limit=500#sthash.YdVMp1us.dpuf");
            HttpResponse response = httpClient.execute(httpGet);
            String responseString = new BasicResponseHandler().handleResponse(response);
            // logger.debug(responseString);
            String[] lines = responseString.split("\r\n|\r|\n");
            String holdOldValue = "";
            StringBuffer buff = new StringBuffer();
            for (String line : lines) {
                //if(count==16) {
                //  logger.debug(line);
                // logger.debug("");
                //String str="\"15\",\"1.0\",\"Parking Area\",\"0-Parking-Mitigation\",\"19\",\"Need cone/sign to identify accessible space\",\"Yes\",\"\",\"van sign\"";
                String[] strArry = line.split(",");
                    /*logger.debug("Section: " + strArry[0]);
                    logger.debug("Category Id: " + strArry[1]);*/
                   /* if(!holdOldValue.equals("") && !strArry[1].toString().equals("0") ) {
                          int flag = getSubCatChange(holdOldValue, strArry[1]);
                          if (flag == 0) {
                              //change in subcategory
                          } else {
                              //no change in subcategory
                          }
                    }
                    holdOldValue= strArry[1];*/
                    /*logger.debug("Category: " + strArry[2]);
                    logger.debug("Subcategory: " + strArry[3]);
                    logger.debug("Question Id: " + strArry[4]);
                    logger.debug("Question: " + strArry[5]);
                    logger.debug("Answer: " + strArry[6]);
                    logger.debug("Data: " + strArry[7]);
                    logger.debug("Comments: " + strArry[8]);*/

                if (strArry[1].replace("\"", "").toString().trim().equals("1")) {
                    buff.append(line + "\n");
                    // logger.debug(line);
                }

                //}
                // count++;
            }

            //  createHtmlPage(buff.toString());
        } catch (Exception e) {
            System.out.print("Exception Value:" + e);
        }
    }

    public void createHtmlPage(String str) {
        String[] lines = str.split("\r\n|\r|\n");
        logger.debug(lines.length);
        String htmlPage = "";
        String htmlPage1 =
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

                        "</head>" +
                        "<body>" +

                        "<table border=\"1\" style=\"width:100%\">" +
                        "  <tr>" +
                        "    <td align=\"center\" height=\"70px\">Name of polling station</td>" +
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
                        "    <td align=\"center\" height=\"70px\" width=\"50%\">Address</td>" +
                        "	<td align=\"center\" height=\"70px\" width=\"50%\">Map</td>" +
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
            String htmlPage2 = "</table>" + "</body>" + "</html>";
            try {
                String fileName = catId.toString().replace("\"", "").trim();
                htmlPage = htmlPage1 + strBuf.toString() + htmlPage2;
                PrintWriter writer = new PrintWriter("E:\\CSUS_Sacro\\CSUS Book Store\\Hackthon Code\\File_Create\\" + fileName + ".html", "UTF-8");
                writer.println(htmlPage);
                writer.close();
            } catch (Exception e) {
                logger.debug("Got Exception during html file creation:" + e);
            }
        }
    }


    public static void main(String[] args) {
        GenerateStaticPages generate = new GenerateStaticPages();
        generate.getData();
    }
}
/**
 * Test for Parsing JSON to create a 2 dimensional array.
 * 
 * @type {string}
 */

var jsonArrayString = '[{"DESCRIPTION":"SacCounty Polling Station ID &quot;15&quot; - 2","URL":"https://farm2.staticflickr.com/1620/25857987942_ab6fe51625_z.jpg"},{"DESCRIPTION":"SacCounty Polling Station ID &quot;15&quot; - 3","URL":"https://farm2.staticflickr.com/1509/25857987902_df097cf21b_z.jpg"},{"DESCRIPTION":"SacCounty Polling Station ID &quot;15&quot; - 4","URL":"https://farm2.staticflickr.com/1631/25346099194_4bdb9f611d_z.jpg"},{"DESCRIPTION":"SacCounty Polling Station ID &quot;15&quot; - 5","URL":"https://farm2.staticflickr.com/1621/25350068583_aa199fee52_z.jpg"},{"DESCRIPTION":"SacCounty Polling Station ID &quot;15&quot; - 1","URL":"https://farm2.staticflickr.com/1719/25952886576_2a8891ceb9_z.jpg"}]'
var jsonArray = JSON.parse(jsonArrayString)
var imagesArray = new Array(jsonArray.length);

i = 0
for (var x in jsonArray) {
    valuePair = JSON.parse(x)
    imagesArray[i] = new Array(2);
    imagesArray[i][0] = jsonArray[x]["URL"]
    imagesArray[i][1] = jsonArray[x]["DESCRIPTION"]
    i++
}

console.log(imagesArray)
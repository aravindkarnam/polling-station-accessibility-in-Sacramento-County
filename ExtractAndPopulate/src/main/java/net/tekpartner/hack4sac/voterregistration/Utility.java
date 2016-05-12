package net.tekpartner.hack4sac.voterregistration;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cgaajula
 * Date: 3/31/16
 * Time: 12:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class Utility {
    public static final String VALUE_DELIMITER = "_";

    public static String mergeValues(String category, String subCategory, String question) {
        String mergedValue = StringUtils.lowerCase(category);
        mergedValue = mergedValue.concat(Utility.VALUE_DELIMITER);
        mergedValue = mergedValue.concat(StringUtils.lowerCase(subCategory));
        mergedValue = mergedValue.concat(Utility.VALUE_DELIMITER);
        mergedValue = mergedValue.concat(StringUtils.lowerCase(question));
        return mergedValue;
    }

    public static String trimmer(String inputString) {
        if (inputString != null) {
            inputString = inputString.replaceFirst("\"", "");
            if (inputString.endsWith("\"")) {
                inputString = inputString.substring(0, inputString.length() - 1);
            }
            inputString = inputString.replace("\"\"", "INCHES");
        } else {
            inputString = StringUtils.EMPTY;
        }

        return inputString;
    }

    public static String camelCase(String inputString) {
        String spaceToUnderscore = inputString.trim().replaceAll(" +", "_").toUpperCase();
        String camelCase = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, spaceToUnderscore);
        return camelCase;
    }

    public static List<String> generateFlickrTags(String flickrTagPrefix, String pollingStationId) {
        List<String> flickrTags = new ArrayList<String>();
        flickrTags.add(flickrTagPrefix.trim().toLowerCase().concat(pollingStationId.trim().toLowerCase()));
        return flickrTags;
    }
}
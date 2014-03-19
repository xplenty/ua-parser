/**
 * Copyright 2012 Twitter, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ua_parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Device parser using ua-parser regexes. Extracts device information from user agent strings.
 *
 * @author 
 */
public class PlatformParser {
  List<PlatformPattern> patterns;

  public PlatformParser(List<PlatformPattern> patterns) {
    this.patterns = patterns;
  }

  public Platform parse(String agentString) {
    if (agentString == null) {
      return null;
    }

    String platform = null;
    for (PlatformPattern p : patterns) {
      if ((platform = p.match(agentString)) != null) {
        break;
      }
    }
    if (platform == null) platform = "Other";	//TODO: verify this

    return new Platform(platform);
  }

  public static PlatformParser fromList(List<Map<String,String>> configList) {
    List<PlatformPattern> configPatterns = new ArrayList<PlatformPattern>();
    for (Map<String,String> configMap : configList) {
      configPatterns.add(PlatformParser.patternFromMap(configMap));
    }
    return new PlatformParser(configPatterns);
  }

  protected static PlatformPattern patternFromMap(Map<String, String> configMap) {
    String regex = configMap.get("regex");
    if (regex == null) {
      throw new IllegalArgumentException("Platform is missing regex");
    }
    return new PlatformPattern(Pattern.compile(regex, Pattern.CASE_INSENSITIVE),
                             configMap.get("device_category"));
  }

  protected static class PlatformPattern {
    private final Pattern pattern;
    private final String family;

    public PlatformPattern(Pattern pattern, String family) {
      this.pattern = pattern;
      this.family = family;
    }

    public String match(String agentString) {
      Matcher matcher = pattern.matcher(agentString);
     
      if (!matcher.find()) {
        return null;
      }

      String family = null;
      if (this.family != null) {        
          family = this.family;
      } 
      return family;
    }
  }

}
/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 *
 */

package se.vgr.util;

/**
 * Gathers information about the user who experienced the problem.
 */
public class UserAgentUtils {

    private static final int OFFSET_ELEVEN = 11;
    private static final int OFFSET_TEN = 10;
    private static final int OFFSET_TWELVE = 12;
    private static final int OFFSET_ONE = 1;
    private static final int OFFSET_THREE = 3;
    private static final int OFFSET_FOUR = 4;
    private static final int OFFSET_FIVE = 5;
    private static final int OFFSET_SIX = 6;
    private static final int OFFSET_SEVEN = 7;
    private static final int OFFSET_EIGHT = 8;
    private static final int OFFSET_NINE = 9;

    private static String getFirstVersionNumber(String userAgent, int position, int numDigits) {
        String ver = getVersionNumber(userAgent, position);
        if (ver == null) {
            return "";
        }
        int i = 0;
        String res = "";
        while (i < ver.length() && i < numDigits) {
            res += String.valueOf(ver.charAt(i));
            i++;
        }
        return res;
    }

    private static String getVersionNumber(String userAgent, int position) {
        if (position < 0) {
            return "";
        }
        StringBuffer res = new StringBuffer();
        int status = 0;

        while (position < userAgent.length()) {
            char c = userAgent.charAt(position);
            switch (status) {
                case 0: // <SPAN class="codecomment"> No valid digits encountered yet</span>
                    if (c == ' ' || c == '/') {
                        break;
                    }
                    if (c == ';' || c == ')') {
                        return "";
                    }
                    status = 1;
                case 1: // <SPAN class="codecomment"> Version number in progress</span>
                    if (c == ';' || c == '/' || c == ')' || c == '(' || c == '[') {
                        return res.toString().trim();
                    }
                    if (c == ' ') {
                        status = 2;
                    }
                    res.append(c);
                    break;
                case 2: // <SPAN class="codecomment"> Space encountered - Might need to end the parsing</span>
                    if (Character.isLetter(c) && Character.isLowerCase(c) || Character.isDigit(c)) {
                        res.append(c);
                        status = 1;
                    } else {
                        return res.toString().trim();
                    }
                    break;
                default:
                    break;
            }
            position++;
        }
        return res.toString().trim();
    }

    private static String[] getArray(String a, String b, String c) {
        String[] res = new String[OFFSET_THREE];
        res[0] = a;
        res[1] = b;
        res[2] = c;
        return res;
    }

    private static String[] getBotName(String userAgent) {
        userAgent = userAgent.toLowerCase();
        int pos = 0;
        String res = null;
        if (userAgent.indexOf("help.yahoo.com/") > -1) {
            pos = userAgent.indexOf("help.yahoo.com/");
            res = "Yahoo";
            pos += OFFSET_SEVEN;
        } else if (userAgent.indexOf("google/") > -1) {
            pos = userAgent.indexOf("google/");
            res = "Google";
            pos += OFFSET_SEVEN;
        } else if (userAgent.indexOf("msnbot/") > -1) {
            pos = userAgent.indexOf("msnbot/");
            res = "MSNBot";
            pos += OFFSET_SEVEN;
        } else if (userAgent.indexOf("googlebot/") > -1) {
            pos = userAgent.indexOf("googlebot/");
            res = "Google";
            pos += OFFSET_TEN;
        } else if (userAgent.indexOf("webcrawler/") > -1) {
            pos = userAgent.indexOf("webcrawler/");
            res = "WebCrawler";
            pos += OFFSET_ELEVEN;
        } else
        // <SPAN class="codecomment"> The following two bots don't have any version number in their User-Agent
        // strings.</span>
        if (userAgent.indexOf("inktomi") > -1) {
            pos = userAgent.indexOf("inktomi");
            res = "Inktomi";
            pos = -1;
        } else if (userAgent.indexOf("teoma") > -1) {
            pos = userAgent.indexOf("teoma");
            res = "Teoma";
            pos = -1;
        }
        if (res == null) {
            return null;
        }
        return getArray(res, res, res + getVersionNumber(userAgent, pos));
    }

    /**
     * Finds out the OS of the user.
     * 
     * @param userAgent
     *            String that contains information about the users OS.
     * @return The OS of the user.
     */
    public static String getOS(String userAgent) {
        if (getBotName(userAgent) != null) {
            return "Bot";
        }
        String[] res = null;
        int pos;
        if (userAgent.indexOf("Windows-NT") > -1) {
            pos = userAgent.indexOf("Windows-NT");
            res = getArray("Win", "WinNT", "Win" + getVersionNumber(userAgent, pos + OFFSET_EIGHT));
        } else if (userAgent.indexOf("Windows NT") > -1) {
            // <SPAN class="codecomment"> The different versions of Windows NT are decoded in the verbosity level
            // 2</span>
            // <SPAN class="codecomment"> ie: Windows NT 5.1 = Windows XP</span>
            if (userAgent.indexOf("Windows NT 5.1") > -1) {
                pos = userAgent.indexOf("Windows NT 5.1");
                res = getArray("Win", "WinXP", "Win" + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
            } else if (userAgent.indexOf("Windows NT 6.0") > -1) {
                pos = userAgent.indexOf("Windows NT 6.0");
                res = getArray("Win", "Vista", "Vista" + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
            } else if (userAgent.indexOf("Windows NT 6.1") > -1) {
                pos = userAgent.indexOf("Windows NT 6.1");
                res = getArray("Win", "Seven", "Seven " + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
            } else if (userAgent.indexOf("Windows NT 5.0") > -1) {
                pos = userAgent.indexOf("Windows NT 5.0");
                res = getArray("Win", "Win2000", "Win" + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
            } else if (userAgent.indexOf("Windows NT 5.2") > -1) {
                pos = userAgent.indexOf("Windows NT 5.2");
                res = getArray("Win", "Win2003", "Win" + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
            } else if (userAgent.indexOf("Windows NT 4.0") > -1) {
                pos = userAgent.indexOf("Windows NT 4.0");
                res = getArray("Win", "WinNT4", "Win" + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
            } else if (userAgent.indexOf("Windows NT)") > -1) {
                pos = userAgent.indexOf("Windows NT)");
                res = getArray("Win", "WinNT", "WinNT");
            } else if (userAgent.indexOf("Windows NT;") > -1) {
                pos = userAgent.indexOf("Windows NT;");
                res = getArray("Win", "WinNT", "WinNT");
            } else {
                res = getArray("Win", "<B>WinNT?</B>", "<B>WinNT?</B>");
            }
        } else if (userAgent.indexOf("Win") > -1) {
            if (userAgent.indexOf("Windows") > -1) {
                if (userAgent.indexOf("Windows 98") > -1) {
                    pos = userAgent.indexOf("Windows 98");
                    res = getArray("Win", "Win98", "Win" + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
                } else if (userAgent.indexOf("Windows_98") > -1) {
                    pos = userAgent.indexOf("Windows_98");
                    res = getArray("Win", "Win98", "Win" + getVersionNumber(userAgent, pos + OFFSET_EIGHT));
                } else if (userAgent.indexOf("Windows 2000") > -1) {
                    pos = userAgent.indexOf("Windows 2000");
                    res = getArray("Win", "Win2000", "Win" + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
                } else if (userAgent.indexOf("Windows 95") > -1) {
                    pos = userAgent.indexOf("Windows 95");
                    res = getArray("Win", "Win95", "Win" + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
                } else if (userAgent.indexOf("Windows 9x") > -1) {
                    pos = userAgent.indexOf("Windows 9x");
                    res = getArray("Win", "Win9x", "Win" + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
                } else if (userAgent.indexOf("Windows ME") > -1) {
                    pos = userAgent.indexOf("Windows ME");
                    res = getArray("Win", "WinME", "Win" + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
                } else if (userAgent.indexOf("Windows 3.1") > -1) {
                    pos = userAgent.indexOf("Windows 3.1");
                    res = getArray("Win", "Win31", "Win" + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
                }
                // <SPAN class="codecomment"> If no version was found, rely on the following code to detect
                // "WinXX"</span>
                // <SPAN class="codecomment"> As some User-Agents include two references to Windows</span>
                // <SPAN class="codecomment"> Ex: Mozilla/5.0 (Windows; U; Win98; en-US; rv:1.5)</span>
            }
            if (res == null) {
                if (userAgent.indexOf("Win98") > -1) {
                    pos = userAgent.indexOf("Win98");
                    res = getArray("Win", "Win98", "Win" + getVersionNumber(userAgent, pos + OFFSET_THREE));
                } else if (userAgent.indexOf("Win31") > -1) {
                    pos = userAgent.indexOf("Win31");
                    res = getArray("Win", "Win31", "Win" + getVersionNumber(userAgent, pos + OFFSET_THREE));
                } else if (userAgent.indexOf("Win95") > -1) {
                    pos = userAgent.indexOf("Win95");
                    res = getArray("Win", "Win95", "Win" + getVersionNumber(userAgent, pos + OFFSET_THREE));
                } else if (userAgent.indexOf("Win 9x") > -1) {
                    pos = userAgent.indexOf("Win 9x");
                    res = getArray("Win", "Win9x", "Win" + getVersionNumber(userAgent, pos + OFFSET_THREE));
                } else if (userAgent.indexOf("WinNT4.0") > -1) {
                    pos = userAgent.indexOf("WinNT4.0");
                    res = getArray("Win", "WinNT4", "Win" + getVersionNumber(userAgent, pos + OFFSET_THREE));
                } else if (userAgent.indexOf("WinNT") > -1) {
                    pos = userAgent.indexOf("WinNT");
                    res = getArray("Win", "WinNT", "Win" + getVersionNumber(userAgent, pos + OFFSET_THREE));
                }
            }
            if (res == null) {
                if (userAgent.indexOf("Windows") > -1) {
                    pos = userAgent.indexOf("Windows");
                    res = getArray("Win", "<B>Win?</B>", "<B>Win?"
                            + getVersionNumber(userAgent, pos + OFFSET_SEVEN) + "</B>");
                } else if (userAgent.indexOf("Win") > -1) {
                    pos = userAgent.indexOf("Win");
                    res = getArray("Win", "<B>Win?</B>", "<B>Win?"
                            + getVersionNumber(userAgent, pos + OFFSET_THREE) + "</B>");
                } else {
                    // <SPAN class="codecomment"> Should not happen at this point</span>
                    res = getArray("Win", "<B>Win?</B>", "<B>Win?</B>");
                }
            }
        } else if (userAgent.indexOf("Mac OS X") > -1) {
            pos=userAgent.indexOf("Mac OS X");
            if (userAgent.indexOf("iPhone") > -1) {
                pos = userAgent.indexOf("iPhone OS");
                res = getArray("Mac", "MacOSX-iPhone", "MacOS-iPhone "
                        + (pos < 0 ? "" : getVersionNumber(userAgent, pos + OFFSET_NINE)));
            } else {
                pos = -1;
                res = getArray("Mac", "MacOSX", "MacOS " + getVersionNumber(userAgent, pos + OFFSET_EIGHT));
            }
        } else if (userAgent.indexOf("Mac_PowerPC") > -1) {
            pos = userAgent.indexOf("Mac_PowerPC");
            res = getArray("Mac", "MacPPC", "MacOS " + getVersionNumber(userAgent, pos + OFFSET_THREE));
        } else if (userAgent.indexOf("Macintosh") > -1) {
            pos = userAgent.indexOf("Macintosh");
            if (userAgent.indexOf("PPC") > -1) {
                res = getArray("Mac", "MacPPC", "MacOS?");
            } else {
                res = getArray("Mac?", "Mac?", "MacOS?");
            }
        } else if (userAgent.indexOf("FreeBSD") > -1) {
            pos = userAgent.indexOf("FreeBSD");
            res = getArray("*BSD", "*BSD FreeBSD", "FreeBSD " + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
        } else if (userAgent.indexOf("OpenBSD") > -1) {
            pos = userAgent.indexOf("OpenBSD");
            res = getArray("*BSD", "*BSD OpenBSD", "OpenBSD " + getVersionNumber(userAgent, pos + OFFSET_SEVEN));
        } else if (userAgent.indexOf("Linux") > -1) {
            pos = userAgent.indexOf("Linux");
            String detail = "Linux " + getVersionNumber(userAgent, pos + OFFSET_FIVE);
            String med = "Linux";
            if (userAgent.indexOf("Ubuntu/") > -1) {
                pos = userAgent.indexOf("Ubuntu/");
                detail = "Ubuntu " + getVersionNumber(userAgent, pos + OFFSET_SEVEN);
                med += " Ubuntu";
            }
            res = getArray("Linux", med, detail);
        } else if (userAgent.indexOf("CentOS") > -1) {
            pos = userAgent.indexOf("CentOS");
            res = getArray("Linux", "Linux CentOS", "CentOS");
        } else if (userAgent.indexOf("NetBSD") > -1) {
            pos = userAgent.indexOf("NetBSD");
            res = getArray("*BSD", "*BSD NetBSD", "NetBSD " + getVersionNumber(userAgent, pos + OFFSET_SIX));
        } else if (userAgent.indexOf("Unix") > -1) {
            pos = userAgent.indexOf("Unix");
            res = getArray("Linux", "Linux", "Linux " + getVersionNumber(userAgent, pos + OFFSET_FOUR));
        } else if (userAgent.indexOf("SunOS") > -1) {
            pos = userAgent.indexOf("SunOS");
            res = getArray("Unix", "SunOS", "SunOS" + getVersionNumber(userAgent, pos + OFFSET_FIVE));
        } else if (userAgent.indexOf("IRIX") > -1) {
            pos = userAgent.indexOf("IRIX");
            res = getArray("Unix", "IRIX", "IRIX" + getVersionNumber(userAgent, pos + OFFSET_FOUR));
        } else if (userAgent.indexOf("SonyEricsson") > -1) {
            pos = userAgent.indexOf("SonyEricsson");
            res = getArray("SonyEricsson", "SonyEricsson", "SonyEricsson"
                    + getVersionNumber(userAgent, pos + OFFSET_TWELVE));
        } else if (userAgent.indexOf("Nokia") > -1) {
            pos = userAgent.indexOf("Nokia");
            res = getArray("Nokia", "Nokia", "Nokia" + getVersionNumber(userAgent, pos + OFFSET_FIVE));
        } else if (userAgent.indexOf("BlackBerry") > -1) {
            pos = userAgent.indexOf("BlackBerry");
            res = getArray("BlackBerry", "BlackBerry", "BlackBerry"
                    + getVersionNumber(userAgent, pos + OFFSET_TEN));
        } else if (userAgent.indexOf("SymbianOS") > -1) {
            pos = userAgent.indexOf("SymbianOS");
            res = getArray("SymbianOS", "SymbianOS", "SymbianOS" + getVersionNumber(userAgent, pos + OFFSET_TEN));
        } else if (userAgent.indexOf("BeOS") > -1) {
            pos = userAgent.indexOf("BeOS");
            res = getArray("BeOS", "BeOS", "BeOS");
        } else if (userAgent.indexOf("Nintendo Wii") > -1) {
            pos = userAgent.indexOf("Nintendo Wii");
            res = getArray("Nintendo Wii", "Nintendo Wii", "Nintendo Wii"
                    + getVersionNumber(userAgent, pos + OFFSET_TEN));
        } else {
            res = getArray("<b>?</b>", "<b>?</b>", "<b>?</b>");
        }
        String result = "";
        for (String s : res) {
            result += s + " ";
        }
        return result;
    }

    /**
     * Finds out the browser of the user.
     * 
     * @param userAgent
     *            String that contains information about the users browser.
     * @return The browser of the user.
     */
    public static String getBrowser(String userAgent) {
        String[] botName;
        if (getBotName(userAgent) != null) {
            botName = getBotName(userAgent);
            return botName[0];
        }
        String[] res = null;
        int pos;
        if (userAgent.indexOf("Lotus-Notes/") > -1) {
            pos = userAgent.indexOf("Lotus-Notes/");
            res = getArray("LotusNotes", "LotusNotes", "LotusNotes"
                    + getVersionNumber(userAgent, pos + OFFSET_TWELVE));
        } else if (userAgent.indexOf("Opera") > -1) {
            pos = userAgent.indexOf("Opera");
            res = getArray("Opera", "Opera" + getFirstVersionNumber(userAgent, pos + OFFSET_FIVE, OFFSET_ONE),
                    "Opera" + getVersionNumber(userAgent, pos + OFFSET_FIVE));
        } else if (userAgent.indexOf("MSIE") > -1) {
            if (userAgent.indexOf("MSIE 6.0") > -1) {
                pos = userAgent.indexOf("MSIE 6.0");
                res = getArray("MSIE", "MSIE6", "MSIE" + getVersionNumber(userAgent, pos + OFFSET_FOUR));
            } else if (userAgent.indexOf("MSIE 5.0") > -1) {
                pos = userAgent.indexOf("MSIE 5.0");
                res = getArray("MSIE", "MSIE5", "MSIE" + getVersionNumber(userAgent, pos + OFFSET_FOUR));
            } else if (userAgent.indexOf("MSIE 5.5") > -1) {
                pos = userAgent.indexOf("MSIE 5.5");
                res = getArray("MSIE", "MSIE5.5", "MSIE" + getVersionNumber(userAgent, pos + OFFSET_FOUR));
            } else if (userAgent.indexOf("MSIE 5.") > -1) {
                pos = userAgent.indexOf("MSIE 5.");
                res = getArray("MSIE", "MSIE5.x", "MSIE" + getVersionNumber(userAgent, pos + OFFSET_FOUR));
            } else if (userAgent.indexOf("MSIE 4") > -1) {
                pos = userAgent.indexOf("MSIE 4");
                res = getArray("MSIE", "MSIE4", "MSIE" + getVersionNumber(userAgent, pos + OFFSET_FOUR));
            } else if (userAgent.indexOf("MSIE 7") > -1) {
                pos = userAgent.indexOf("MSIE 7");
                res = getArray("MSIE", "MSIE7", "MSIE" + getVersionNumber(userAgent, pos + OFFSET_FOUR));
            } else if (userAgent.indexOf("MSIE 8") > -1) {
                pos = userAgent.indexOf("MSIE 8");
                res = getArray("MSIE", "MSIE8", "MSIE" + getVersionNumber(userAgent, pos + OFFSET_FOUR));
            } else {
                res = getArray("MSIE", "<B>MSIE?</B>", "<B>MSIE?"
                        + getVersionNumber(userAgent, userAgent.indexOf("MSIE") + OFFSET_FOUR) + "</B>");
            }
        } else if (userAgent.indexOf("Gecko/") > -1) {
            pos = userAgent.indexOf("Gecko/");
            res = getArray("Gecko", "Gecko", "Gecko"
                    + getFirstVersionNumber(userAgent, pos + OFFSET_FIVE, OFFSET_FOUR));
            if (userAgent.indexOf("Camino/") > -1) {
                pos = userAgent.indexOf("Camino/");
                res[1] += "(Camino)";
                res[2] += "(Camino" + getVersionNumber(userAgent, pos + OFFSET_SEVEN) + ")";
            } else if (userAgent.indexOf("Chimera/") > -1) {
                pos = userAgent.indexOf("Chimera/");
                res[1] += "(Chimera)";
                res[2] += "(Chimera" + getVersionNumber(userAgent, pos + OFFSET_EIGHT) + ")";
            } else if (userAgent.indexOf("Firebird/") > -1) {
                pos = userAgent.indexOf("Firebird/");
                res[1] += "(Firebird)";
                res[2] += "(Firebird" + getVersionNumber(userAgent, pos + OFFSET_NINE) + ")";
            } else if (userAgent.indexOf("Phoenix/") > -1) {
                pos = userAgent.indexOf("Phoenix/");
                res[1] += "(Phoenix)";
                res[2] += "(Phoenix" + getVersionNumber(userAgent, pos + OFFSET_EIGHT) + ")";
            } else if (userAgent.indexOf("Galeon/") > -1) {
                pos = userAgent.indexOf("Galeon/");
                res[1] += "(Galeon)";
                res[2] += "(Galeon" + getVersionNumber(userAgent, pos + OFFSET_SEVEN) + ")";
            } else if (userAgent.indexOf("Firefox/") > -1) {
                pos = userAgent.indexOf("Firefox/");
                res[1] += "(Firefox)";
                res[2] += "(Firefox" + getVersionNumber(userAgent, pos + OFFSET_EIGHT) + ")";
            } else if (userAgent.indexOf("Netscape/") > -1) {
                pos = userAgent.indexOf("Netscape/");
                if (userAgent.indexOf("Netscape/6") > -1) {
                    pos = userAgent.indexOf("Netscape/6");
                    res[1] += "(NS6)";
                    res[2] += "(NS" + getVersionNumber(userAgent, pos + OFFSET_NINE) + ")";
                } else if (userAgent.indexOf("Netscape/7") > -1) {
                    pos = userAgent.indexOf("Netscape/7");
                    res[1] += "(NS7)";
                    res[2] += "(NS" + getVersionNumber(userAgent, pos + OFFSET_NINE) + ")";
                } else {
                    res[1] += "(NS?)";
                    res[2] += "(NS?" + getVersionNumber(userAgent, userAgent.indexOf("Netscape/") + OFFSET_NINE)
                            + ")";
                }
            }
        } else if (userAgent.indexOf("Netscape/") > -1) {
            pos = userAgent.indexOf("Netscape/");
            if (userAgent.indexOf("Netscape/4") > -1) {
                pos = userAgent.indexOf("Netscape/4");
                res = getArray("NS", "NS4", "NS" + getVersionNumber(userAgent, pos + OFFSET_NINE));
            } else {
                res = getArray("NS", "NS?", "NS?" + getVersionNumber(userAgent, pos + OFFSET_NINE));
            }
        } else if (userAgent.indexOf("Chrome/") > -1) {
            pos = userAgent.indexOf("Chrome/");
            res = getArray("KHTML", "KHTML(Chrome)", "KHTML(Chrome"
                    + getVersionNumber(userAgent, pos + OFFSET_SIX) + ")");
        } else if (userAgent.indexOf("Safari/") > -1) {
            pos = userAgent.indexOf("Safari/");
            res = getArray("KHTML", "KHTML(Safari)", "KHTML(Safari"
                    + getVersionNumber(userAgent, pos + OFFSET_SIX) + ")");
        } else if (userAgent.indexOf("Konqueror/") > -1) {
            pos = userAgent.indexOf("Konqueror/");
            res = getArray("KHTML", "KHTML(Konqueror)", "KHTML(Konqueror"
                    + getVersionNumber(userAgent, pos + OFFSET_NINE) + ")");
        } else if (userAgent.indexOf("KHTML") > -1) {
            pos = userAgent.indexOf("KHTML");
            res = getArray("KHTML", "KHTML?", "KHTML?(" + getVersionNumber(userAgent, pos + OFFSET_FIVE) + ")");
        } else if (userAgent.indexOf("NetFront") > -1) {
            pos = userAgent.indexOf("NetFront");
            res = getArray("NetFront", "NetFront", "NetFront " + getVersionNumber(userAgent, pos + OFFSET_EIGHT));
        } else {
            pos = -1;
            // <SPAN class="codecomment"> We will interpret Mozilla/4.x as Netscape Communicator is and only if
            // x</span>
            // <SPAN class="codecomment"> is not 0 or 5</span>
            if (userAgent.indexOf("Mozilla/4.") == 0 && userAgent.indexOf("Mozilla/4.0") < 0
                    && userAgent.indexOf("Mozilla/4.5 ") < 0) {
                res = getArray("Communicator", "Communicator", "Communicator"
                        + getVersionNumber(userAgent, pos + OFFSET_EIGHT));
            } else {
                res = getArray("<B>?</B>", "<B>?</B>", "<B>?</B>");
            }
        }
        String result = "";
        for (String s : res) {
            result += s + " ";
        }
        return result;
    }

}
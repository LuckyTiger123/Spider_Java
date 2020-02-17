import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.logging.Level;
import java.util.regex.PatternSyntaxException;

public class Verifier {
    public static String rootPath = "C:/Users/74783/Desktop/课程/软件质量保证/Spider/";

    public static void main(String[] args) {
        System.out.println("校验器");
        System.out.println("");
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        System.out.println("输入Spider数据根目录的绝对路径:(Spider后请加上/)");
        rootPath = sc.nextLine();
        System.out.println("");
        System.out.println("选择需要校验的文件:");
        System.out.println("1.股票信息文件原始数据");
        System.out.println("2.沪深A股近3年数据对比");
        System.out.println("3.文章文件元数据");
        System.out.println("4.种子文件元数据");
        System.out.println("5.退出");
        System.out.print("输入选择:");
        choice = sc.nextInt();
        while (choice != 5) {
            if (choice == 1) {
                System.out.println("选择需要校验的股票");
                System.out.println("1.沪深A股");
                System.out.println("2.上证A股");
                System.out.println("3.深证A股");
                System.out.println("4.新股");
                System.out.println("5.中小板");
                System.out.println("6.创业板");
                System.out.println("7.科创板");
                System.out.println("8.B股");
                int selection = sc.nextInt();
                checkStock(selection);
            } else if (choice == 2) {
                checkFileControl();
            } else if (choice == 3) {
                System.out.println("选择需要校验的文件:");
                System.out.println("1.财经导读");
                System.out.println("2.经济时评");
                System.out.println("3.股市评论");
                System.out.println("4.国内经济");
                System.out.println("5.证券聚焦");
                System.out.println("6.国际经济");
                int selection = sc.nextInt();
                checkContentControl(selection);
            } else if (choice == 4) {
                loginAndCheck();
            } else {

            }
            System.out.println("done");
            System.out.println("");
            System.out.println("选择需要校验的文件:");
            System.out.println("1.股票信息文件原始数据");
            System.out.println("2.沪深A股近3年数据对比");
            System.out.println("3.文章文件元数据");
            System.out.println("4.种子文件元数据");
            System.out.println("5.退出");
            System.out.print("输入选择:");
            choice = sc.nextInt();
        }
        System.out.println("感谢使用！");
    }

    public static void checkStock(int choice) {
        System.out.println("Loading...");
        if (choice == 1) {      //沪深A股
            String url_part1 = "http://75.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112400738288620723957_1577111180013&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:13,m:0+t:80,m:1+t:2,m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577111180014]";
            int count = 194;
            checkStockInfo("hs_a.txt", url_part1, url_part2, count);
        } else if (choice == 2) {     //上证A股
            String url_part1 = "http://88.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112409807426270270241_1577171994683&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:1+t:2,m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577171994684";
            int count = 82;
            checkStockInfo("sh_a.txt", url_part1, url_part2, count);
        } else if (choice == 3) {       //深证A股
            String url_part1 = "http://23.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112406444892471119386_1577172345517&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:13,m:0+t:80&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577172345518";
            int count = 113;
            checkStockInfo("sz_a.txt", url_part1, url_part2, count);
        } else if (choice == 4) {       //新股
            String url_part1 = "http://98.push2.eastmoney.com/api/qt/clist/get?cb=jQuery11240926339674295998_1577172564977&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f26&fs=m:0+f:8,m:1+f:8&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f26,f22,f11,f62,f128,f136,f115,f152&_=1577172564978";
            int count = 65;
            checkStockInfo("newshares.txt", url_part1, url_part2, count);
        } else if (choice == 5) {       //中小板
            String url_part1 = "http://58.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112407835109344323208_1577172847465&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:13&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577172847466";
            int count = 48;
            checkStockInfo("sme_board.txt", url_part1, url_part2, count);
        } else if (choice == 6) {        //创业板
            String url_part1 = "http://38.push2.eastmoney.com/api/qt/clist/get?cb=jQuery1124010120287787517879_1577173069930&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:80&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577173069931";
            int count = 40;
            checkStockInfo("gem_board.txt", url_part1, url_part2, count);
        } else if (choice == 7) {         //科创板
            String url_part1 = "http://59.push2.eastmoney.com/api/qt/clist/get?cb=jQuery1124047448481512778873_1577173246915&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577173246916";
            int count = 4;
            checkStockInfo("kcb_board.txt", url_part1, url_part2, count);
        } else if (choice == 8) {          //B股
            String url_part1 = "http://54.push2.eastmoney.com/api/qt/clist/get?cb=jQuery1124024859410470985233_1577173598409&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:7,m:1+t:3&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577173598410";
            int count = 6;
            checkStockInfo("b_board.txt", url_part1, url_part2, count);
        } else return;
    }

    public static void checkStockInfo(String fileName, String url_part1, String url_part2, int count) {
        String openFilepath = rootPath + "data/" + fileName;
        File readFile = new File(openFilepath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(readFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("文件路径定义错误！");
            return;
        }
        String tempString = null;
        ArrayList<stockInfo> stockList = new ArrayList<stockInfo>();
        try {
            while ((tempString = reader.readLine()) != null) {
                stockInfo temp = new stockInfo();
                String[] item = tempString.split(" ");
                temp.stockName = item[0];
                temp.stockID = item[1];
                stockList.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int flag = 1;

        for (int i = 1; i <= count; i++) {
            String url = url_part1 + String.valueOf(i) + url_part2;
            try {
                WebClient webClient = new WebClient(BrowserVersion.CHROME);
                webClient.getOptions().setJavaScriptEnabled(true);
                webClient.getOptions().setCssEnabled(false);
                webClient.getOptions().setThrowExceptionOnScriptError(false);
                webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
                webClient.getOptions().setTimeout(10 * 1000);
                HtmlPage page = webClient.getPage(url);
                webClient.waitForBackgroundJavaScript(30 * 1000);
                String pageAsXml = page.asXml();
                Document doc = Jsoup.parse(pageAsXml);
                Elements bodyTest = doc.select("body");
                String opTest = bodyTest.text();
                flag = checkStockPage(opTest, stockList);
                if (flag == 0) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (flag == 0) {
            System.out.println("存在差异");
        } else {
            System.out.println("数据比对成功!");
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int checkStockPage(String opString, ArrayList<stockInfo> stockList) {
        int begin = opString.indexOf("diff");
        String content = opString.substring(begin + 7, opString.length() - 5);
        String[] item = content.split("},");
        int flag = 1;
        for (String a : item) {
            int f14 = a.indexOf("\"f14\"");
            int f14_end = a.indexOf(",", f14);
            String stockName = a.substring(f14 + 7, f14_end - 1);
            int f12 = a.indexOf("\"f12\"");
            int f12_end = a.indexOf(",", f12);
            String stockID = a.substring(f12 + 7, f12_end - 1);
            for (int i = 0; i < stockList.size(); i++) {
                if (!stockList.get(i).stockName.equals(stockName) && stockList.get(i).stockID.equals(stockID)) {
                    System.out.println(stockName + " " + stockID + " " + stockList.get(i).stockName + " " + stockList.get(i).stockID);
                    flag = 0;
                    break;
                }
            }
            if (flag == 0) {
                break;
            } else {
                System.out.println(stockName + " " + stockID + " " + "checked!");
            }
        }
        return flag;
    }

    public static void checkContentControl(int choice) {
        System.out.println("Loading...");
        if (choice == 1) {
            String url_part1 = "http://finance.eastmoney.com/a/ccjdd_";
            String dirName = "财经导读";
            int count = 25;
            checkContent(url_part1, dirName, count);
        } else if (choice == 2) {
            String url_part1 = "http://finance.eastmoney.com/news/cjjsp_";
            String dirName = "经济时评";
            int count = 25;
            checkContent(url_part1, dirName, count);
        } else if (choice == 3) {
            String url_part1 = "http://finance.eastmoney.com/news/cgspl_";
            String dirName = "股市评论";
            int count = 25;
            checkContent(url_part1, dirName, count);
        } else if (choice == 4) {
            String url_part1 = "http://finance.eastmoney.com/news/cgnjj_";
            String dirName = "国内经济";
            int count = 25;
            checkContent(url_part1, dirName, count);
        } else if (choice == 5) {
            String url_part1 = "http://finance.eastmoney.com/a/czqyw_";
            String dirName = "证券聚焦";
            int count = 25;
            checkContent(url_part1, dirName, count);
        } else if (choice == 6) {
            String url_part1 = "http://finance.eastmoney.com/news/cgjjj_";
            String dirName = "国际经济";
            int count = 25;
            checkContent(url_part1, dirName, count);
        }
    }

    public static void checkContent(String url_part1, String dirName, int count) {
        String openDir = rootPath + "content_Metadata/" + dirName;
        int flag = 1;
        for (int i = 1; i <= count; i++) {
            String url = url_part1 + String.valueOf(i) + ".html";
            try {
                Document doc = Jsoup.connect(url).get();
                Elements div = doc.select("div.repeatList");
                Elements item_li = div.select("li");
                Iterator it = item_li.iterator();
                int j = 1;
                while (it.hasNext()) {
                    Element element = (Element) it.next();
                    String enterUrl = element.select("a[href]").first().attr("href");
                    flag = checkContentGet(openDir, enterUrl);
                    if (flag == 0) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (flag == 0) {
            System.out.println("存在差异");
        } else {
            System.out.println("数据比对成功!");
        }
    }

    public static int checkContentGet(String filepath, String url) {
        int flag = 1;
        String[] opContent = url.split("/");
        String fileName = opContent[opContent.length - 1];
        fileName = fileName.substring(0, fileName.length() - 5);
        String openFilepath = filepath + "/" + fileName + ".txt";
        File readFile = new File(openFilepath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(readFile));
        } catch (FileNotFoundException e) {
            System.out.println(fileName + "不在本地");
            return 1;
        }

        try {
            Document doc = Jsoup.connect(url).get();
            if (doc.select("div.newsContent").size() == 0) {
                String title = doc.select("h1").first().text();
                String sourceOrigin = doc.select("span.article-meta-cate").first().text();
                String file_name = reader.readLine();
                String file_url = reader.readLine();
                String file_title = reader.readLine();
                String file_source = reader.readLine();
                if (!file_name.equals(fileName) || !file_url.equals(url) || !title.equals(file_title) || !file_source.equals(sourceOrigin)) {
                    flag = 0;
                    System.out.println("存在偏差：");
                    System.out.println(fileName + " " + url + " " + title + " " + sourceOrigin);
                    System.out.println(file_name + " " + file_url + " " + file_title + " " + file_source);
                }
            } else {
                Element content = doc.select("div.newsContent").first();
                String title = content.select("h1").first().text();
                String time = content.select("div.time").first().text();
                String sourceOrigin;
                if (content.select("div.source.data-source").size() == 0) {
                    sourceOrigin = "";
                } else {
                    sourceOrigin = content.select("div.source.data-source").first().text();
                }
                String file_name = reader.readLine();
                String file_url = reader.readLine();
                String file_title = reader.readLine();
                String file_time = reader.readLine();
                String file_source = reader.readLine();
                if (!fileName.equals(file_name) || !url.equals(file_url) || !title.equals(file_title) || !time.equals(file_time) || !sourceOrigin.equals(file_source)) {
                    flag = 0;
                    System.out.println("存在偏差：");
                    System.out.println(fileName + " " + url + " " + title + " " + time + " " + sourceOrigin);
                    System.out.println(file_name + " " + file_url + " " + file_title + " " + file_time + " " + file_source);
                }
            }
        } catch (IOException e) {
            return 1;
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (flag == 1) {
            System.out.println(fileName + " " + "checked!");
        }
        return flag;
    }

    public static void loginAndCheck() {
        System.out.println("Loading...");
        String url_part1 = "http://www.nexushd.org/torrents.php?inclbookmarked=0&incldead=0&spstate=0&pktype=0&page=";
        int count = 200;
        int flag = 1;
        String filepath = rootPath + "seed";

        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setCssEnabled(false);
        client.setAjaxController(new NicelyResynchronizingAjaxController());
        client.getOptions().setThrowExceptionOnScriptError(false);
        try {
            HtmlPage page = client.getPage("http://www.nexushd.org/login.php");
            HtmlInput userName = (HtmlInput) page.getElementsByName("username").get(0);
            HtmlInput password = (HtmlInput) page.getElementsByName("password").get(0);
            userName.setAttribute("value", "LuckyTiger");
            password.setAttribute("value", "981015");
            HtmlButton btn = (HtmlButton) page.getElementsByTagName("button").get(0);
            HtmlPage new_page = btn.click();
            for (int i = 0; i < count; i++) {
                String url = url_part1 + String.valueOf(i);
                HtmlPage getSeed = client.getPage(url);
                String pageAsXml = getSeed.asXml();
                flag = seedCheck(pageAsXml, filepath);
                if (flag == 0) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FailingHttpStatusCodeException e) {
            e.printStackTrace();
        }
        if (flag == 0) {
            System.out.println("存在差异");
        } else {
            System.out.println("数据比对成功!");
        }
    }

    public static int seedCheck(String pageAsXml, String filepath) {
        int flag = 1;
        Document doc = Jsoup.parse(pageAsXml);
        Elements tr_block = doc.select("tr.free_bg");
        Iterator tr = tr_block.iterator();
        while (tr.hasNext()) {
            Element element = (Element) tr.next();
            String type = element.select("img").first().attr("alt");
            String title = element.select("tr.free_bg").first().select("td.embedded").first().select("a").first().attr("title");
            String introduction;
            try {
                introduction = element.select("td.embedded").first().text().replaceAll(title, "").trim();
            } catch (PatternSyntaxException e) {
                introduction = element.select("td.embedded").first().text().substring(title.length()).trim();
            }
            String pageUrl = "http://www.nexushd.org/" + element.select("tr.free_bg").first().select("td.embedded").first().select("a").first().attr("href");
            String time;
            try {
                time = element.select("td.rowfollow.nowrap").last().select("span").first().attr("title");
            } catch (NullPointerException e) {
                continue;
            }
            String downloadUrl = "http://www.nexushd.org/" + element.select("td.embedded").last().select("a").first().attr("href");
            String id = downloadUrl.split("id=")[1];
            String fileSize = element.select("td.rowfollow").get(4).text();
            String seedNum = element.select("td.rowfollow").get(5).text();
            String announcer = element.select("td.rowfollow").last().text();
            String openFilepath = filepath + "/" + id + ".txt";
            File readFile = new File(openFilepath);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(readFile));
            } catch (FileNotFoundException e) {
                System.out.println(id + "不在本地");
                continue;
            }
            try {
                String file_id = reader.readLine();
                String file_type = reader.readLine();
                String file_title = reader.readLine();
                String file_introduction = reader.readLine();
                String file_pageUrl = reader.readLine();
                String file_time = reader.readLine();
                String file_fileSize = reader.readLine();
                String file_seedNum = reader.readLine();
                String file_announcer = reader.readLine();
                String file_downloadUrl = reader.readLine();
                if (!id.equals(file_id) || !type.equals(file_type) || !title.equals(file_title)
                        || !pageUrl.equals(file_pageUrl) || !time.equals(file_time) || !fileSize.equals(file_fileSize)
                        || !announcer.equals(file_announcer) || !downloadUrl.equals(file_downloadUrl)) {
                    flag = 0;
                    System.out.println("存在偏差：");
                    System.out.println(id + " " + type + " " + title + " " + introduction + " " + pageUrl + " " + time + " " +
                            fileSize + " " + seedNum + " " + announcer + " " + downloadUrl);
                    System.out.println(file_id + " " + file_type + " " + file_title + " " + file_introduction + " " + file_pageUrl + " " + file_time + " " +
                            file_fileSize + " " + file_seedNum + " " + file_announcer + " " + file_downloadUrl);
                }
            } catch (IOException e) {
                System.out.println("文件" + id + "读取错误");
            }
            tr.next();
            if (flag == 0) {
                break;
            } else {
                System.out.println(id + " " + "checked!");
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public static void checkFileControl() {
        System.out.println("Loading...");
        String url_part1 = "http://quotes.money.163.com/trade/lsjysj_";
        String url_part2 = ".html?year=2019&season=4";
        ArrayList<String> hs_a_stockID = getStockID();
        String url = null;
        int flag = 1;
        for (int i = 0; i < hs_a_stockID.size(); i++) {
            url = url_part1 + hs_a_stockID.get(i) + url_part2;
            String getDownloadID = getDataFile(url);
            if (getDownloadID == null) {
                System.out.println(hs_a_stockID.get(i) + " has missed");
                continue;
            }
            String downloadID = getDownloadID.substring(1, 8);
            flag = getCsvOnceForAll(downloadID);
            if (flag == 1) {
                System.out.println(downloadID.substring(1) + " has been checked!");
            } else {
                System.out.println(downloadID.substring(1) + " 数据有不同");
                break;
            }
        }
        if (flag == 0) {
            System.out.println("存在差异");
        } else {
            System.out.println("数据比对成功!");
        }
    }

    public static int getCsvOnceForAll(String stockID) {
        File file = new File(rootPath + "dataFile/" + stockID.substring(1) + ".csv");
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        int flag = 1;
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println(stockID.substring(1) + " 在本地没有数据");
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] webStream = getDownloadStream(stockID);
        if (Arrays.equals(filecontent, webStream)) {
            flag = 1;
        } else {
            flag = 0;
        }
        return flag;
    }

    public static byte[] getDownloadStream(String stockID) {
        String url_part1 = "http://quotes.money.163.com/service/chddata.html?code=";
        String url_part2 = "&start=20170101&end=20191227&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP";
        String url = url_part1 + stockID + url_part2;
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            InputStream inputStream = conn.getInputStream();
            byte[] getData = readInputStream(inputStream);
            if (inputStream != null) {
                inputStream.close();
            }
            return getData;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("无法获取网络数据");
            return null;
        }
    }

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static String getDataFile(String url) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);
        String result = null;
        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setTimeout(10 * 1000);
            HtmlPage page = webClient.getPage(url);
            webClient.waitForBackgroundJavaScript(30 * 1000);
            String pageAsXml = page.asXml();
            Document doc = Jsoup.parse(pageAsXml);
            Element logo_area = doc.select("div.logo_area").first();
            result = logo_area.select("a").get(5).attr("href");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static ArrayList<String> getStockID() {
        ArrayList<String> stockIDList = new ArrayList<String>();
        String openFilepath = rootPath + "data/" + "hs_a.txt";
        File readFile = new File(openFilepath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(readFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("hs_a.txt无法找到！");
            return stockIDList;
        }
        String tempString = null;
        try {
            while ((tempString = reader.readLine()) != null) {
                String[] item = tempString.split(" ");
                String stockID = item[item.length - 5];
                stockIDList.add(stockID);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockIDList;
    }
}

class stockInfo {
    public String stockName;
    public String stockID;

    stockInfo() {

    }
}

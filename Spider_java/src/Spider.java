import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.commons.logging.LogFactory;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.logging.Level;
import java.util.regex.PatternSyntaxException;

public class Spider {
    public static String rootPath = "C:/Users/74783/Desktop/课程/软件质量保证/Spider/";

    public static void main(String[] args) {
        System.out.println("网络爬虫");
        System.out.println("");
        Scanner sc = new Scanner(System.in);
        System.out.println("输入Spider数据根目录的绝对路径，请先自行建立Spider文件夹:(Spider后请加上/)");
        rootPath = sc.nextLine();
        System.out.println("");
        int choice = 0;
        System.out.println("选择需要爬取的文件信息:");
        System.out.println("1.沪深个股当日数据（来自东方财富）");
        System.out.println("2.沪深A股近三年的历史数据（需要先爬取沪深个股）");
        System.out.println("3.经济类文章（来自东方财富）");
        System.out.println("4.N站种子（需要登入）");
        System.out.println("5.离开");
        System.out.print("输入选择：");
        choice = sc.nextInt();
        while (choice != 4) {
            if (choice == 1) {
                getStockControl(1);
                getStockControl(2);
                getStockControl(3);
                getStockControl(4);
                getStockControl(5);
                getStockControl(6);
                getStockControl(7);
                getStockControl(8);
            } else if (choice == 2) {
                fileInfoGet();
            } else if (choice == 3) {
                getContentControl(1);
                getContentControl(2);
                getContentControl(3);
                getContentControl(4);
                getContentControl(5);
                getContentControl(6);
            } else if (choice == 4) {
                loginAndGet();
            } else {
            }
            System.out.println("done");
            System.out.println("");
            System.out.println("选择需要爬取的文件信息:");
            System.out.println("1.沪深个股当日数据（来自东方财富）");
            System.out.println("2.沪深A股近三年的历史数据（需要先爬取沪深个股）");
            System.out.println("3.经济类文章（来自东方财富）");
            System.out.println("4.N站种子（需要登入）");
            System.out.println("5.离开");
            System.out.print("输入选择：");
            choice = sc.nextInt();
        }
        System.out.println("感谢使用");
    }

    public static void getStockControl(int choice) {
        if (choice == 1) {      //爬取沪深A股
            String url_part1 = "http://75.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112400738288620723957_1577111180013&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:13,m:0+t:80,m:1+t:2,m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577111180014]";
            int count = 194;
            getStockInfo("hs_a.txt", url_part1, url_part2, count);
        } else if (choice == 2) {     //上证A股
            String url_part1 = "http://88.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112409807426270270241_1577171994683&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:1+t:2,m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577171994684";
            int count = 82;
            getStockInfo("sh_a.txt", url_part1, url_part2, count);
        } else if (choice == 3) {       //深证A股
            String url_part1 = "http://23.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112406444892471119386_1577172345517&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:13,m:0+t:80&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577172345518";
            int count = 113;
            getStockInfo("sz_a.txt", url_part1, url_part2, count);
        } else if (choice == 4) {       //新股
            String url_part1 = "http://98.push2.eastmoney.com/api/qt/clist/get?cb=jQuery11240926339674295998_1577172564977&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f26&fs=m:0+f:8,m:1+f:8&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f26,f22,f11,f62,f128,f136,f115,f152&_=1577172564978";
            int count = 65;
            getStockInfo("newshares.txt", url_part1, url_part2, count);
        } else if (choice == 5) {       //中小板
            String url_part1 = "http://58.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112407835109344323208_1577172847465&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:13&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577172847466";
            int count = 48;
            getStockInfo("sme_board.txt", url_part1, url_part2, count);
        } else if (choice == 6) {        //创业板
            String url_part1 = "http://38.push2.eastmoney.com/api/qt/clist/get?cb=jQuery1124010120287787517879_1577173069930&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:80&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577173069931";
            int count = 40;
            getStockInfo("gem_board.txt", url_part1, url_part2, count);
        } else if (choice == 7) {         //科创板
            String url_part1 = "http://59.push2.eastmoney.com/api/qt/clist/get?cb=jQuery1124047448481512778873_1577173246915&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:1+t:23&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577173246916";
            int count = 4;
            getStockInfo("kcb_board.txt", url_part1, url_part2, count);
        } else if (choice == 8) {          //B股
            String url_part1 = "http://54.push2.eastmoney.com/api/qt/clist/get?cb=jQuery1124024859410470985233_1577173598409&pn=";
            String url_part2 = "&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:7,m:1+t:3&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f22,f11,f62,f128,f136,f115,f152&_=1577173598410";
            int count = 6;
            getStockInfo("b_board.txt", url_part1, url_part2, count);
        } else return;

    }

    public static void getStockInfo(String fileName, String url_part1, String url_part2, int count) {
        File dir = new File(rootPath + "data");
        dir.mkdirs();
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);

        File outPut = new File(rootPath + "data/" + fileName);
        try {
            outPut.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                opStockInfo(opTest, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void opStockInfo(String opString, String fileName) {

        int begin = opString.indexOf("diff");
        String content = opString.substring(begin + 7, opString.length() - 5);
        String[] item = content.split("},");
        try {
            FileWriter writer = new FileWriter(rootPath + "data/" + fileName, true);
            BufferedWriter bw = new BufferedWriter(writer);
            for (String a : item) {
                int f14 = a.indexOf("\"f14\"");
                int f14_end = a.indexOf(",", f14);
                String stockName = a.substring(f14 + 7, f14_end - 1);
                int f12 = a.indexOf("\"f12\"");
                int f12_end = a.indexOf(",", f12);
                String stockID = a.substring(f12 + 7, f12_end - 1);
                int f2 = a.indexOf("\"f2\"");
                int f2_end = a.indexOf(",", f2);
                String currentPrice = a.substring(f2 + 5, f2_end);
                int f3 = a.indexOf("\"f3\"");
                int f3_end = a.indexOf(",", f3);
                String changePercent = a.substring(f3 + 5, f3_end);
                int f4 = a.indexOf("\"f4\"");
                int f4_end = a.indexOf(",", f4);
                String change = a.substring(f4 + 5, f4_end);
                int f5 = a.indexOf("\"f5\"");
                int f5_end = a.indexOf(",", f5);
                String volumn = a.substring(f5 + 5, f5_end);
                System.out.println(stockName + " " + stockID + " " + currentPrice + " " + changePercent + " " + change + " " + volumn);
                bw.write(stockName + " " + stockID + " " + currentPrice + " " + changePercent + " " + change + " " + volumn + "\r\n");
            }
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getContentControl(int choice) {
        if (choice == 1) {
            String url_part1 = "http://finance.eastmoney.com/a/ccjdd_";
            String dirName = "财经导读";
            int count = 25;
            getContent(url_part1, dirName, count);
        } else if (choice == 2) {
            String url_part1 = "http://finance.eastmoney.com/news/cjjsp_";
            String dirName = "经济时评";
            int count = 25;
            getContent(url_part1, dirName, count);
        } else if (choice == 3) {
            String url_part1 = "http://finance.eastmoney.com/news/cgspl_";
            String dirName = "股市评论";
            int count = 25;
            getContent(url_part1, dirName, count);
        } else if (choice == 4) {
            String url_part1 = "http://finance.eastmoney.com/news/cgnjj_";
            String dirName = "国内经济";
            int count = 25;
            getContent(url_part1, dirName, count);
        } else if (choice == 5) {
            String url_part1 = "http://finance.eastmoney.com/a/czqyw_";
            String dirName = "证券聚焦";
            int count = 25;
            getContent(url_part1, dirName, count);
        } else if (choice == 6) {
            String url_part1 = "http://finance.eastmoney.com/news/cgjjj_";
            String dirName = "国际经济";
            int count = 25;
            getContent(url_part1, dirName, count);
        }

    }


    public static void getContent(String url_part1, String dirName, int count) {
        File dir_F = new File(rootPath + "content_Metadata");
        dir_F.mkdirs();
        File dir = new File(rootPath + "content_Metadata/" + dirName);
        dir.mkdirs();
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
                    contentGet(rootPath + "content_Metadata/" + dirName, enterUrl);
                    System.out.println(dirName + " " + i + " " + j++);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void contentGet(String filepath, String url) {
        String[] opContent = url.split("/");
        String fileName = opContent[opContent.length - 1];
        fileName = fileName.substring(0, fileName.length() - 5);
        try {
            Document doc = Jsoup.connect(url).get();
            if (doc.select("div.newsContent").size() == 0) {
                Element content = doc.select("div.wrapper").first();
                String title = doc.select("h1").first().text();
                String sourceOrigin = doc.select("span.article-meta-cate").first().text();
                File outPut = new File(filepath + "/" + fileName + ".txt");
                outPut.createNewFile();
                FileWriter writer = new FileWriter(filepath + "/" + fileName + ".txt");
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write(fileName + "\r\n");
                bw.write(url + "\r\n");
                bw.write(title + "\r\n");
                bw.write(sourceOrigin + "\r\n");
                bw.close();
                writer.close();
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

                String commentNumber;
                if (content.select("span.cNumShow.num").size() == 0) {
                    commentNumber = String.valueOf(0);
                } else {
                    commentNumber = content.select("span.cNumShow.num").first().text();
                }
                String joinNumber;
                if (content.select("span.num.ml5").size() == 0) {
                    joinNumber = String.valueOf(0);
                } else {
                    joinNumber = content.select("span.num.ml5").first().text();
                }
                String abstractContent;
                if (content.select("div.b-review").size() == 0) {
                    abstractContent = "";
                } else {
                    abstractContent = content.select("div.b-review").first().text();
                }
                String resEdit = content.select("p.res-edit").first().text();
                File outPut = new File(filepath + "/" + fileName + ".txt");
                outPut.createNewFile();
                FileWriter writer = new FileWriter(filepath + "/" + fileName + ".txt");
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write(fileName + "\r\n");
                bw.write(url + "\r\n");
                bw.write(title + "\r\n");
                bw.write(time + "\r\n");
                bw.write(sourceOrigin + "\r\n");
                bw.write(commentNumber + "\r\n");
                bw.write(joinNumber + "\r\n");
                bw.write(abstractContent + "\r\n");
                bw.write(resEdit + "\r\n");
                bw.close();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            contentGet(filepath, url);
        }
    }

    public static void loginAndGet() {
        String url_part1 = "http://www.nexushd.org/torrents.php?inclbookmarked=0&incldead=0&spstate=0&pktype=0&page=";
        int count = 200;

        File dir = new File(rootPath + "seed");
        dir.mkdirs();

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
                seedAnalysis(pageAsXml);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FailingHttpStatusCodeException e) {
            e.printStackTrace();
        }
    }

    public static void seedAnalysis(String pageAsXml) {
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
            String time = element.select("td.rowfollow.nowrap").last().select("span").first().attr("title");
            String downloadUrl = "http://www.nexushd.org/" + element.select("td.embedded").last().select("a").first().attr("href");
            String id = downloadUrl.split("id=")[1];
            String fileSize = element.select("td.rowfollow").get(4).text();
            String seedNum = element.select("td.rowfollow").get(5).text();
            String announcer = element.select("td.rowfollow").last().text();
            File writeFile = new File(rootPath + "seed/" + id + ".txt");
            try {
                writeFile.createNewFile();
                FileWriter writer = new FileWriter(rootPath + "seed/" + id + ".txt");
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write(id + "\r\n");
                bw.write(type + "\r\n");
                bw.write(title + "\r\n");
                bw.write(introduction + "\r\n");
                bw.write(pageUrl + "\r\n");
                bw.write(time + "\r\n");
                bw.write(fileSize + "\r\n");
                bw.write(seedNum + "\r\n");
                bw.write(announcer + "\r\n");
                bw.write(downloadUrl + "\r\n");
                bw.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            tr.next();
            System.out.println(title);
        }
    }

    public static void fileInfoGet() {
        File dir_F = new File(rootPath + "dataFile");
        dir_F.mkdirs();
        String url_part1 = "http://quotes.money.163.com/trade/lsjysj_";
        String url_part2 = ".html?year=2019&season=4";
        ArrayList<String> hs_a_stockID = getStockID();
        String url = null;
        for (int i = 0; i < hs_a_stockID.size(); i++) {
            url = url_part1 + hs_a_stockID.get(i) + url_part2;
            String getDownloadID = getDataFile(url);
            if (getDownloadID == null) {
                System.out.println(hs_a_stockID.get(i) + " has missed");
                continue;
            }
            String downloadID = getDownloadID.substring(1, 8);
            download(downloadID);
            System.out.println(downloadID.substring(1) + " has been downloaded");
        }
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

    public static void download(String stockID) {
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
            File saveDir = new File(rootPath + "dataFile");
            String fileName = stockID.substring(1) + ".csv";
            File write_file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(write_file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("下载异常");
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
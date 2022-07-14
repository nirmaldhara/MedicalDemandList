package med.demand.util;
import com.sun.javafx.print.Units;
import static com.sun.javafx.print.Units.MM;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import med.demand.enums.Message;
import med.demand.model.DemandListDetails;


public class PrintUtil {

    public static void print() throws MalformedURLException {
    //    printstatus.setText("print : printing...");
        Paper photo = null;
        try {
            WebView webview = new WebView();
            final WebEngine webengine = webview.getEngine();
            Printer printer = Printer.getDefaultPrinter();
            Constructor<Paper> c = Paper.class.getDeclaredConstructor(String.class,
                    double.class, double.class, Units.class);
            c.setAccessible(true);
            try {
                photo = c.newInstance("595X842", 210, 297, MM);
            } catch (InstantiationException ex) {
                Logger.getLogger(PrintUtil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(PrintUtil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(PrintUtil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(PrintUtil.class.getName()).log(Level.SEVERE, null, ex);
            }

            PageLayout pageLayout = printer.createPageLayout(photo,
                    PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
            PrinterJob job = PrinterJob.createPrinterJob();

            job.getJobSettings().setPageLayout(pageLayout);

            JobSettings js = job.getJobSettings();

            Double w = js.getPageLayout().getLeftMargin();
            Double h = js.getPageLayout().getRightMargin();

            System.out.println("=------------" + w + "   " + h);
            if (job != null) {
                //System.out.println(job.getJobSettings().getPageLayout());

                File myFile = new File("demandlist.html");
                URL myUrl = myFile.toURI().toURL();
                webengine.load(myUrl.toString());

                webengine.print(job);


                job.endJob();

                //printstatus.setText("print : Done");
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(PrintUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(PrintUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean createDemandListFile(List<DemandListDetails> demandListDetails) throws IOException {
        boolean flag = false;
        StringBuilder htmlBuilder = new StringBuilder();
        int i=0;
        String color="";
        if (demandListDetails.size()>0) {

            htmlBuilder.append("<html>");
            htmlBuilder.append("<head><title>Barik Medicals</title></head>");
            htmlBuilder.append("<body width=300>");
            htmlBuilder.append("<table width=300 CELLSPACING=0 CELLPADDING=0>");
            htmlBuilder.append("<tr><td nowrap colspan=3  valign=middle><center><img src='add_blue.png' width=40/> <font size=4>Barik Medicals</font></center></td></tr>");
            htmlBuilder.append("<tr><td nowrap colspan=3 ><font size=2>Sabang , mob- 9000000000</font></td></tr>");
            htmlBuilder.append("<tr><td nowrap colspan=3 >~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~</td></tr>");
            htmlBuilder.append("<tr height=30><td nowrap colspan=3>&nbsp;</td></tr>");
            htmlBuilder.append("<tr><td nowrap><font size=4>Medicines</font></td><td nowrap><font size=4>Suppliers</font></td><td nowrap><font size=4>Quantity</font></td></tr>");
            for(DemandListDetails dld:demandListDetails)
            {

                if(i%2==0)
                    color="#f0ebeb";
                else
                    color="";
                htmlBuilder.append("<tr height=30 style=\"background-color:"+color+"\"><td nowrap >"+ dld.getMedName()+"</td><td nowrap>"+dld.getSupName()+"</td><td nowrap>"+dld.getQuantity()+"</td></tr>");
                i++;
            }


                       htmlBuilder.append("</table>");
            htmlBuilder.append("</body>");
            htmlBuilder.append("</html>");
            try (FileOutputStream oS = new FileOutputStream(new File("demandlist.html"))) {
                oS.write(htmlBuilder.toString().getBytes());
                flag = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Util.showErrorMessage(Message.NO_DATA.get(), Message.CAN_NOT_PRINT.get());
        }
        return flag;

    }

}

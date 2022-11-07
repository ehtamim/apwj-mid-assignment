package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.domain.taxCalculator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    double taxableInc=0;double taxLiability=0;double totalRebate=0;double netTax=0;double totalInc=0;
    int givenID;
    public DataSource dataSource;

    public HomeController(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @RequestMapping("/upDel")
    public String upDel(@ModelAttribute("taxID") String id,Model model) throws SQLException {
        givenID=Integer.parseInt(id);
        int g=givenID;
        model.addAttribute("dataID",givenID);
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from tax");
        while(resultSet.next()) {
            if(resultSet.getLong(1)==givenID)
            {
            model.addAttribute("dataSal",resultSet.getDouble(2));
            model.addAttribute("dataHR",resultSet.getDouble(3));
            model.addAttribute("dataMA",resultSet.getDouble(4));
            model.addAttribute("dataCon",resultSet.getDouble(5));
            model.addAttribute("dataFB",resultSet.getDouble(6));}
        }
        return "upDel";
    }
    @RequestMapping("/delete")
    public String delete(Model model) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("delete from tax where id=?");
        statement.setDouble(1, givenID);
        statement.execute();
        return "show";
    }
    @RequestMapping("/update")
    public String update(@ModelAttribute("upBasicSalary") String upBS, @ModelAttribute("upHouseRent") String hr1, @ModelAttribute("upMedicalAllowance") String ma, @ModelAttribute("upConveyance") String con, @ModelAttribute("upFestivalBonus") String fb, Model model) throws SQLException {
        //double a1=Double.parseDouble(bs1);
        double ubs=Double.parseDouble(upBS);
        double b1=Double.parseDouble(hr1);
        double c=Double.parseDouble(ma);
        double d=Double.parseDouble(con);
        double e=Double.parseDouble(fb);
        calculation(ubs*12,b1*12,c*12,d*12,e);
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("update tax set  basicSalary=?,houseRent=?,medicalAllowance=?,conveyance=?,festivalBonus=?,taxAbleIncome=?,taxLiability=?,rebate=?,netTax=? where id = ?");

        statement.setDouble(1, ubs);
        statement.setDouble(2, b1);
        statement.setDouble(3, c);
        statement.setDouble(4, d);
        statement.setDouble(5, e);
        statement.setDouble(6, taxableInc);
        statement.setDouble(7, taxLiability);
        statement.setDouble(8, totalRebate);
        statement.setDouble(9, netTax);
        statement.setInt(10, givenID);

        statement.execute();

        model.addAttribute("totalIncome",totalInc);
        model.addAttribute("taxableIncome",taxableInc);
        model.addAttribute("grossTax",taxLiability);
        model.addAttribute("rebate",totalRebate);
        model.addAttribute("netTax",netTax);


        return "upDel";
    }
    @RequestMapping("/show")
    public String list(Model model) throws SQLException {
        List<taxCalculator> taxCalculators = new ArrayList<taxCalculator>();
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from tax");
        while (resultSet.next())
        {
            long i=resultSet.getLong(1);
            double a=resultSet.getDouble(2);
            double b=resultSet.getDouble(3);
            double c=resultSet.getDouble(4);
            double d=resultSet.getDouble(5);
            double e=resultSet.getDouble(6);
            double f=resultSet.getDouble(7);
            double g=resultSet.getDouble(8);
            double h=resultSet.getDouble(9);
            double j=resultSet.getDouble(10);

            taxCalculator t=new taxCalculator(i,a,b,c,d,e,f,g,h,j);
            taxCalculators.add(t);
        }

        model.addAttribute("taxCalculator", taxCalculators);
        return "show";
    }

    @RequestMapping("/tax")
    public String tax(Model model) {
        return "calculate";
    }

    @RequestMapping("/submit")
    public String show(@ModelAttribute("basicSalary") String bs, @ModelAttribute("houseRent") String hr, @ModelAttribute("medicalAllowance") String ma, @ModelAttribute("conveyance") String con, @ModelAttribute("festivalBonus") String fb, Model model) throws SQLException {
        double a=Double.parseDouble(bs);
        double b=Double.parseDouble(hr);
        double c=Double.parseDouble(ma);
        double d=Double.parseDouble(con);
        double e=Double.parseDouble(fb);
        calculation(a*12,b*12,c*12,d*12,e);
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("insert into tax(basicSalary,houseRent,medicalAllowance,conveyance,festivalBonus,taxAbleIncome,taxLiability,rebate,netTax) values (?, ?,?,?, ?,?,?, ?,?)");

        statement.setDouble(1, a);
        statement.setDouble(2, b);
        statement.setDouble(3, c);
        statement.setDouble(4, d);
        statement.setDouble(5, e);
        statement.setDouble(6, taxableInc);
        statement.setDouble(7, taxLiability);
        statement.setDouble(8, totalRebate);
        statement.setDouble(9, netTax);

        statement.execute();

        model.addAttribute("totalIncome",totalInc);
        model.addAttribute("taxableIncome",taxableInc);
        model.addAttribute("grossTax",taxLiability);
        model.addAttribute("rebate",totalRebate);
        model.addAttribute("netTax",netTax);


        return "calculate";
    }

    public void calculation(double basicSalary,double houseRent,double medicalAllowance,double conveyance,double festivalBonus)
    {
        double govHouseRent=25000*12;
        double halfBasicSal=basicSalary/2;
        double taxableHR=0;
        double taxableCon=0;
        double taxableMA=0;
        double taxableFB=0;
        totalInc=basicSalary+houseRent+medicalAllowance+festivalBonus+conveyance;
        double rebateAble=0;
        if (govHouseRent>halfBasicSal)
        {
            taxableHR = houseRent - halfBasicSal;
        }
        else
        {
            taxableHR=houseRent-govHouseRent;
        }
        if (taxableHR>0)
        {
            taxableHR=taxableHR;
        }
        else
        {
            taxableHR=0.0;
        }

        if (conveyance>30000)
        {
            taxableCon=conveyance-30000;
        }
        else
        {
            taxableCon=0;
        }
        if (medicalAllowance>60000)
        {
            taxableMA=medicalAllowance-60000;
        }
        else
        {
            taxableMA=0;
        }
        taxableFB=festivalBonus;
        taxableInc=basicSalary+taxableCon+taxableFB+taxableHR+taxableMA;
        double remainingTaxableInc=0;
        double check=0;
        if (taxableInc>300000)
        {
            remainingTaxableInc=taxableInc-300000;
            if (remainingTaxableInc>100000)
            {
                taxLiability=taxLiability+5000;
                remainingTaxableInc=remainingTaxableInc-100000;
                if (remainingTaxableInc>300000)
                {
                    taxLiability=taxLiability+30000;
                    remainingTaxableInc=remainingTaxableInc-300000;
                    if(remainingTaxableInc>400000)
                    {
                        taxLiability=taxLiability+60000;
                        remainingTaxableInc=remainingTaxableInc-400000;
                        if(remainingTaxableInc>500000)
                        {
                            taxLiability=taxLiability+100000;
                            remainingTaxableInc=remainingTaxableInc-500000;
                            if (remainingTaxableInc>0)
                            {
                                taxLiability = taxLiability + (remainingTaxableInc * 0.25);
                            }
                        }
                        else
                        {
                            taxLiability=taxLiability+(remainingTaxableInc*0.2);
                        }
                    }
                    else
                    {
                        taxLiability=taxLiability+(remainingTaxableInc*0.15);
                    }
                }
                else
                {
                    taxLiability=taxLiability+(remainingTaxableInc*0.1);
                }
            }
            else
            {
                taxLiability=remainingTaxableInc*0.05;
            }
        }
        else
        {
            taxLiability=0;
        }
        if (taxableInc>10000000)
        {
            rebateAble=10000000*0.25;
        }
        else
        {
            rebateAble=taxableInc*0.25;
        }
        totalRebate=rebateAble*0.15;
        netTax=taxLiability-totalRebate;
        //showInfo(taxableInc,taxLiability,rebateAble,netTax);
    }
    public String showInfo(double taxInc,double taxLia,double reb,double nTax,Model model)
    {
        return "calculate";
    }
}

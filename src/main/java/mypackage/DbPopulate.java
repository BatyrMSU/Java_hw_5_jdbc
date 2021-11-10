package mypackage;

import dao.ProductDAO;
import dao.OrganizationDAO;
import dao.WaybillDAO;
import dao.WaybillPositionDAO;
import entities.Product;
import entities.Organization;
import entities.Waybill;
import entities.WaybillPosition;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DbPopulate {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void populate(Connection connection) {

        // creating products records
        final var productsDao = new ProductDAO(connection);
        List<String> productsNames = Arrays.asList("Printer A", "Laptop A", "Desk", "Monitor B", "TV", "Mouse C",
                                            "Printer B", "Remote control", "Laptop B", "Mouse A", "Book C");
        List<Integer> codes = Arrays.asList(123456, 234567, 345678, 456789, 567890, 678901, 789012, 890123,
                                            901234, 412345, 987654);
        int productsNum = productsNames.size();
        for (int i = 0; i < productsNum; i++) {
            productsDao.save(new Product(i, productsNames.get(i), codes.get(i)));
        }



        // creating organizations records
        final var organizationsDao = new OrganizationDAO(connection);
        List<String> orgnames = Arrays.asList("Company A", "Company B", "Company C", "Company D", "Company E", "Company F",
                                                "Company G", "Company H", "Company I", "Company J", "Company K", "Company L",
                                                    "Company M", "Company N");
        List<Integer> orginns = Arrays.asList(741454, 444816, 370311, 883786, 983740, 928120,
                                                165912, 399184, 576217, 815858, 484535, 324573,
                                                    477801, 884470);
        List<Integer> orgaccounts = Arrays.asList(4412804, 5407186, 8863361, 2560081, 9203052, 1709653,
                                                    9319519, 7588225, 7003729, 7462745, 3199987, 6110797,
                                                        7872542, 9146797);
        for (int i = 0; i < orgnames.size(); i++) {
            organizationsDao.save(new Organization(i, orgnames.get(i), orginns.get(i), orgaccounts.get(i)));
        }
        // Some companies without products
        organizationsDao.save(new Organization(100, "Company W", 432622, 5425825));
        organizationsDao.save(new Organization(145, "Company Z", 232653, 3213425));



        // creating waybills records
        final var waybillsDAO = new WaybillDAO(connection);
        Random random = new Random();
        int waybillsNum = 50;
        for (int i = 0; i < waybillsNum; i++) {
            waybillsDAO.save(new Waybill(i, new Date(random.nextInt(3)+118, random.nextInt(11)+1, random.nextInt(29)+1),
                                                        random.nextInt((orgnames.size()))));
        }


        // creating waybills_positions records
        final var waybillsPositionsDAO = new WaybillPositionDAO(connection);
        for (int i = 0; i < 300; i++) {
            waybillsPositionsDAO.save(new WaybillPosition(i, random.nextInt(waybillsNum), random.nextInt(10000)+1000,
                                                            random.nextInt(productsNum), random.nextInt(100)+1));
        }


    }


}

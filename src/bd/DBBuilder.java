package bd;

import com.company.Reactor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBBuilder {    //нужно чтобы представить Бд в джаве...
    private String DBName;
    ArrayList<Regions> Regions = new ArrayList<>();
    ArrayList<Countries> Countries = new ArrayList<>();
    ArrayList<Companies> Companies = new ArrayList<>();
    ArrayList<Sites> Sites = new ArrayList<>();
    ArrayList<Units> Units = new ArrayList<>();


    public DBBuilder(String DBName) {
        this.DBName = DBName;

    }

    public DBBuilder() {
    }

    public String getDBName() {
        return DBName;
    }

    public void setDBName(String DBName) {
        this.DBName = DBName;
    }

    String getAllQuery(String s) {
        String SQLQuery = "SELECT * FROM " + s; //сам s ередаем в другом классе
        return SQLQuery;
    }

    public void handle (ResultSet res, String TabName, List<Reactor> Re) throws SQLException, SQLException {
        while (res.next()) {
            switch (TabName) {
                case ("regions") -> {
                    if (!rowRegionHandle(res).getName().equals("NO DATA")) {//если строчка не пустая, то мы добавляем её
                        Regions.add(rowRegionHandle(res));
                    }
                }
                case ("countries") -> {
                    if (!rowCountryHandle(res).getName().equals("NO DATA")) {
                        Countries.add(rowCountryHandle(res));
                    }
                }
                case ("companies") -> {
                    if (!rowCompanyHandle(res).getName().equals("NO DATA")) {
                        Companies.add(rowCompanyHandle(res));
                    }
                }
                case ("sites") -> Sites.add(rowSiteHandle(res));

                case ("units") -> {
                    String as = rowUnitHandle(res, Re).getStatus().trim();
                    if (as.equals("in operation")) {       //добавляем только действующие рекакторы с таким статусом
                        Units.add(rowUnitHandle(res, Re));

                    }
                }
                default -> throw new Error(" Нет такой таблицы");//срабатывает тогда, когда не подошел не один из case
            }

        }

    }

    private Regions rowRegionHandle(ResultSet res) throws SQLException {
        Regions regions = new Regions();
        regions.setId(res.getInt("regions.id"));
        regions.setName(res.getString("regions.region_name").trim());//trim - удаляет пробелы в начале и конце строки
        return regions;
    }

    private Countries rowCountryHandle(ResultSet res) throws SQLException {
        Countries countries = new Countries();
        countries.setId(res.getInt("countries.id"));
        countries.setName(res.getString("countries.country_name").trim());
        countries.setRegion(res.getInt("countries.region_id"));
        return countries;
    }

    private Companies rowCompanyHandle(ResultSet res) throws SQLException {
        Companies companies = new Companies();
        companies.setId(res.getInt("companies.id"));
        companies.setName(res.getString("companies.companies_name").trim());



        Sites.forEach(a -> {            //сравниваем названия реактора из файла и из бд
            try {
                if (a.getCompany() == res.getInt("companies.id")) {
                    companies.setCountry(a.getCountry());
                }
            } catch (SQLException ex) {

            }
        });

        return companies;
    }

    private Sites rowSiteHandle(ResultSet res) throws SQLException {
        Sites sites = new Sites();
        sites.setId(res.getInt("sites.id"));
        sites.setName(res.getString("sites.npp_name").trim());
        sites.setCompany(res.getInt("sites.owner_id"));
        sites.setCountry(res.getInt("sites.place"));

        return sites;
    }

    private Units rowUnitHandle(ResultSet res, List<Reactor> Re) throws SQLException {
        Units units = new Units();
        units.setName(res.getString("units.unit_name").trim());
        units.setClasss(res.getString("units.class").trim());
        if (res.getString("units.load_factor") != null) {
            units.setLoad_factor(res.getDouble("units.load_factor"));
        } else {
            units.setLoad_factor(90.0); //если ничего не написано в ячейке, то пишем 90
        }
        units.setSite(res.getInt("units.site"));
        units.setStatus(res.getString("units.status").trim());
        units.setTermal_capasity(res.getDouble("units.thermal_capacity"));


        Re.forEach(a -> {
            if (a.getClasss().equals(units.getClas())) {
                units.setBurnup(a.getBurnup());
            }
        });

        return units;
    }

    public ArrayList<Regions> getRegions() {
        return this.Regions;
    }

    public ArrayList<Countries> getCountries() {
        return this.Countries;
    }

    public ArrayList<Companies> getCompanies() {
        return this.Companies;
    }

    public ArrayList<Sites> getSites() {
        return this.Sites;
    }

    public ArrayList<Units> getUnits() {
        return this.Units;
    }
}
//site - это атомная станция
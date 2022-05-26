package bd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class Read { //здесь мы просито заполняем табличку данными
    public void ForCompanies(JTable t, ArrayList<Companies> Companies) {
        DefaultTableModel model = (DefaultTableModel) t.getModel();
        Companies.forEach(a -> model.addRow(new Object[]{a.getName(), a.getFuelConsuption()}));
    }

    public void ForCountries(JTable t, ArrayList<Countries> Countries) {
        DefaultTableModel model = (DefaultTableModel) t.getModel();
        Countries.forEach(a -> model.addRow(new Object[]{a.getName(), a.getFuelConsuption()}));
    }

    public void ForRegions(JTable t, ArrayList<Regions> Regions) {
        DefaultTableModel model = (DefaultTableModel) t.getModel();
        Regions.forEach(a -> model.addRow(new Object[]{a.getName(), a.getFuelConsuption()}));
    }

}

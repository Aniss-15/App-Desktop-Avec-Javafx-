package services;

import java.sql.SQLException;
import java.util.List;

public interface Iservice<F>{
        public void addFormation(F F) ;
        public void updateFormation(F F );
        public void DeleteFormation(F F);
        public List<F>AfficherFormation();


}

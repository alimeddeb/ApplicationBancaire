package tp_AliMeddeb_2GinfoA;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class Compte_epargneTest {

    @Test
    public void test_period(){
        LocalDate date_today = LocalDate.now();
        LocalDate date_2m_ago = LocalDate.parse("2021-01-01");
        Period p = Period.between(date_today, date_2m_ago);
        System.out.println("year: " + p.getYears() + " | months: " + p.getMonths());
    }

}
package ltu;

import static ltu.CalendarFactory.getCalendar;
import static org.junit.Assert.*;
import java.io.IOException;

import org.junit.Test;
import java.util.Calendar;
import ltu.PaymentImpl;

//public class PaymentTest
//{
//    @Test
//    public void getMonthlyAmount()
//    {
//        assertEquals(9094, );
//    }
//
//}
public class PaymentTest
{

    private int FULL = 9904;
    private int HALF = 4960;
    private int ZERO = 0;
    private int FULL_SUBSIDY = 2816;
    private int HALF_SUBSIDY = 1396;
    private int FULL_LOAN = 7088;
    private int HALF_LOAN = 3564;


    @Test
    public void testAge() throws IOException{
        PaymentImpl p = new PaymentImpl(getCalendar());

        assertEquals(ZERO, p.getMonthlyAmount("19970201-8138",0,100,100));//19year

        assertEquals(FULL, p.getMonthlyAmount("19960201-8138",0,100,100));//20 years
        assertEquals(FULL, p.getMonthlyAmount("19950201-8138",0,100,100));//21 years

        assertEquals(FULL_SUBSIDY, p.getMonthlyAmount("19600201-8138",0,100,100));//56 years
        assertEquals(ZERO, p.getMonthlyAmount("19590201-8138",0,100,100));//57 years

        assertEquals(FULL_SUBSIDY, p.getMonthlyAmount("19690801-8138",0,100,100));//47 years
        assertEquals(FULL_SUBSIDY, p.getMonthlyAmount("19680801-8138",0,100,100));//48 years
    }

    @Test
    public void testStudyPace() throws IOException{
        PaymentImpl p = new PaymentImpl(getCalendar());

        assertEquals(FULL, p.getMonthlyAmount("19950201-8138",0,100,100));//100% study pace
        assertEquals(HALF, p.getMonthlyAmount("19950201-8138",0,99,100));//99% study pace
        assertEquals(HALF, p.getMonthlyAmount("19950201-8138",0,50,100));//50% study pace
        assertEquals(ZERO, p.getMonthlyAmount("19950201-8138",0,49,100));//49% study pace
    }

    @Test
    public void testStudyCompletion() throws IOException{
        PaymentImpl p = new PaymentImpl(getCalendar());

        assertEquals(FULL, p.getMonthlyAmount("19950201-8138",0,100,100));//100% completion
        assertEquals(FULL, p.getMonthlyAmount("19950201-8138",0,100,99));//99% Completion
        assertEquals(FULL, p.getMonthlyAmount("19950201-8138",0,100,50));//50% completion
        assertEquals(ZERO, p.getMonthlyAmount("19950201-8138",0,100,49));//49% completiion
    }

    @Test
    public void testIncome() throws IOException{
        PaymentImpl p = new PaymentImpl(getCalendar());

        assertEquals(FULL, p.getMonthlyAmount("19950201-8138",85812,100,100));//100% under limit 
        assertEquals(FULL, p.getMonthlyAmount("19950201-8138",85813,100,100));//100% at limit 
        assertEquals(ZERO, p.getMonthlyAmount("19950201-8138",85814,100,100));//100% over limit
        assertEquals(HALF, p.getMonthlyAmount("19950201-8138",128721,50,100));//50% under limit
        assertEquals(HALF, p.getMonthlyAmount("19950201-8138",128722,50,100));//50% at limit
        assertEquals(ZERO, p.getMonthlyAmount("19950201-8138",128723,50,100));//50% over limit
    }
}
import java.util.*;
public class Polynomial{
   private int maxSol;
   private double[] co;
   public Polynomial(ArrayList<Double> a){
      co = new double[a.size()];
      for(int i = 0; i < a.size(); i++)
         co[i] = a.get(i);
      maxSol = a.size()-1;
   }
   public int getMaxSol(){
      return maxSol;
   }
   public double[] getCo(){
      return co;
   }
   public double[] getRange(){
      double[] range = new double[2];
      List<Double> abs = new ArrayList<Double>();
      for(Double x : co)
         abs.add(Math.abs(x));
      double f = abs.get(0);
      abs.remove(0);
      for(int i = 0; i < abs.size(); i++)
         abs.set(i, abs.get(i) / f);
      double sum = 0;
      for(Double d : abs)
         sum += d;
      range[0] = -1 * Math.min(Math.max(1, sum), 1 + Collections.max(abs));
      range[1] = Math.min(Math.max(1, sum), 1 + Collections.max(abs));
      return range;
   }
   public double solve(double x){
      double s = co[co.length-1];
      for(int i = 0; i < co.length-1; i++){
         s += (Math.pow(x, co.length - i - 1) * co[i]);
      }
      //System.out.println(s);
      return s;
   }
}
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.File;

public class GeneticPolynomial{
    static double MIN_RANGE = -50;
    static double MAX_RANGE = 50;
    static double MUTATION_RATE = 0.3;
    static double MUTATION_SIZE = 0.5;
    static int CHILDREN = 10;
    public static HashMap<Integer, Double> solutionSet;
    public static HashMap<Integer, Double> parentPolySet;
    public static int max;
    public static void main(String[] args) throws IOException {
      Scanner input = new Scanner(System.in);
      ArrayList<Double> li = new ArrayList<Double>(); 
      double[] range = new double[2];
      double[] solutions;
      System.out.println("Send a series of individual coefficients. Enter a non-double character to end the input. Please keep values between " + MIN_RANGE + " and " + MAX_RANGE + ".");
      while(true){
         try{
            li.add(input.nextDouble());
         }
         catch(Exception e){
         break;
         }
      }
      PrintStream stdout = System.out;
      Polynomial targetPoly = new Polynomial(li);
      solutionSet = populateSet(targetPoly);
      int graphMax = Integer.MIN_VALUE;
      for(Map.Entry<Integer, Double> k : solutionSet.entrySet()){
         if(k.getValue() > graphMax){
            graphMax = (int)Math.round(k.getValue());
         }
      }
      max = graphMax;
      System.setOut(new PrintStream(new FileOutputStream("targetSols.txt")));
      for(Map.Entry<Integer, Double> k : solutionSet.entrySet()){
         System.out.println(k.getKey() + "," + k.getValue());
      }
      Polynomial parentPoly = genRandomPoly(targetPoly);
      parentPolySet = populateSet(parentPoly);
      Polynomial[] children = new Polynomial[CHILDREN];
      double[] distances = new double[CHILDREN];
      double minDist;
      int index;
      while(true){
         minDist = Double.MAX_VALUE;
         index = 0;
         for(int i = 0; i < CHILDREN; i++){
            children[i] = mutatePoly(parentPoly);
            distances[i] = distance(solutionSet, populateSet(children[i]));
            if(distances[i] < minDist){
               minDist = distances[i];
               index = i;
            }
         }
         if(distances[index] < distance(parentPolySet, solutionSet)){
            parentPoly = children[index];
            parentPolySet = populateSet(parentPoly);
            System.setOut(new PrintStream(new FileOutputStream("parentSols.txt")));
            for(Map.Entry<Integer, Double> k : parentPolySet.entrySet()){
               System.out.println(k.getKey() + "," + k.getValue());
            }
            System.setOut(stdout);
            System.out.println(minDist);
         }
      }
   }
   
   public static HashMap<Integer, Double> populateSet(Polynomial poly){
      HashMap<Integer, Double> solutionSet = new HashMap<Integer, Double>();
      for(int i = -100; i <= 100; i+= 2){
         solutionSet.put(i, poly.solve(i));
      }
      return solutionSet;
   }
   
   public static Polynomial genRandomPoly(Polynomial poly){
      ArrayList<Double> li = new ArrayList<Double>(); 
      for(int i = 0; i < poly.getCo().length; i++){
         li.add(ThreadLocalRandom.current().nextDouble(MIN_RANGE, MAX_RANGE));
      }
     return new Polynomial(li);
   }
   
   public static double distance(HashMap<Integer, Double> x, HashMap<Integer, Double> y){
      double totalDist = 0;
      for(int i = -100; i < 100; i+= 2){
         totalDist += Math.abs(x.get(i) - y.get(i));
      }
      return totalDist;
   }
   
   public static Polynomial mutatePoly(Polynomial poly){
      double[] oldCo = poly.getCo();
      for(int i = 0; i < oldCo.length; i++){
         if(Math.random() <= MUTATION_RATE){
            if(Math.random() < 0.5){ //add to the value
               oldCo[i] = (double) Math.min(100, oldCo[i] + MUTATION_SIZE);
            }
            else{ //subtract from the value
               oldCo[i] = (double) Math.max(-100, oldCo[i] - MUTATION_SIZE);
            }
         }
      }
      ArrayList<Double> list = doubleToList(oldCo);
      return new Polynomial(list);
   }
   
   public static ArrayList<Double> doubleToList(double[] oldCo){ //convert a double array to a list
      ArrayList<Double> list = new ArrayList<Double>();
      for(int i = 0; i < oldCo.length; i++){
         list.add(oldCo[i]);  
      }
      return list;
   }
}

package mlqproj;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.InputMismatchException;
public class MLQProj 
{ 
    
   
    public static void main(String[] args)
    {
        ProcessAttribt [] tempArray=null;
        ArrayList<ProcessAttribt> Q1=new ArrayList<ProcessAttribt>();;
        ArrayList<ProcessAttribt> Q2=new ArrayList<ProcessAttribt>();;
        ArrayList<String> CPUChart=new ArrayList<String>();;
        double averageTAT,averageWT,averageRT;
       
        int n,arrivaltime,cpuburst,priority;
        Scanner sc=new Scanner(System.in);
        while(true){
        int op=mainMenu();
       switch(op)
               {
                        case 1:
                                    CPUChart=new ArrayList<String>();
                                    tempArray=null;
                                    n=0;
                                    Q1=new ArrayList<ProcessAttribt>();
                                    Q2=new ArrayList<ProcessAttribt>();
                                   System.out.print("Enter number of processes :");
                                    n =sc.nextInt();
                                    tempArray=new ProcessAttribt[n];
                                    for(int i=0;i<n ;i++)
                                    {
                                        System.out.println("Enter Details of Process: P" + (i+1));
                                       System.out.print("Enter Arrival Time :");
                                      do{ 
                                         
                                          arrivaltime=readDigit();
                                          if(arrivaltime<0)
                                              System.out.println("The Entered not Valid!!!");
                                          else
                                              break;
                                        }while(true);
                                       System.out.print("Enter CPU Burst :");
                                       do{ 
                                         
                                          cpuburst=readDigit();
                                          if(cpuburst<0)
                                              System.out.println("The Entered not Valid!!!");
                                          else
                                              break;
                                        }while(true);
                                       System.out.print("Enter Priority :");
                                       do{ 
                                         
                                          priority=readDigit();
                                          if(priority<0)
                                              System.out.println("The Entered not Valid!!!");
                                          else
                                              break;
                                        }while(true);
                                       while(!(priority>=1 && priority<=2))
                                       {
                                           System.out.println("Priority must be 1 or 2");
                                           priority=sc.nextInt();
                                       }
                                         tempArray[i]=new ProcessAttribt("P" +  (i+1),priority,cpuburst,arrivaltime);
                                    }
                                    
                                    initilizeQ1AndQ2(tempArray, Q1, Q2);
                                                                        
                                    break;
				
                        case 2:
                            if(tempArray!=null)
                              screenShowDeails(tempArray);
                            else
                                System.out.println("You have to Initialize  PCB Array First");
                                break;
                        case 3:
                          scheduleingProcedure(CPUChart,Q1,Q2);
                           for(int i =0;i<Q1.size();i++)
                                tempArray[i]=Q1.get(i);
                           int j=0;
                           for(int i =Q1.size();i<tempArray.length;i++)
                                 tempArray[i]=Q2.get(j++);
                           averageTAT= getAverageTAT(tempArray);
                           averageWT= getAverageWT(tempArray);
                           averageRT= getAverageRT(tempArray);
                           System.out.print("CPU chart for sceduling is : [");
                          for(int i=0;i<CPUChart.size();i++)
                              System.out.print(CPUChart.get(i)+" | ");
                          System.out.println(" ]");
                          
                           screenShowDeails2(averageTAT,averageWT,averageRT,tempArray);
                                break;
                        case 4:
                                System.exit(0);
                        default:
                                System.out.println("You Have Entered wrong Selection Please Try again");
         }
        }
         
         
    }
      public static void scheduleingProcedure(ArrayList<String> CPUChart,ArrayList<ProcessAttribt> Q1,ArrayList<ProcessAttribt> Q2)
    {
        
        int clock=0;
        int index1=0,index2=0,c=0;
        do
        {
            index1= Find_SJF(clock,Q1);
                 if(index1!=-1)
                 {
                   Q1.get(index1).setStrat_Time(clock);
                   CPUChart.add( Q1.get(index1).getProc_ID());
                   Q1.get(index1).setTermination_Time(clock+Q1.get(index1).getCPU_burst());
                   Q1.get(index1).setTurn_Round(Q1.get(index1).getTermination_Time()-Q1.get(index1).getArival_Time());
                   Q1.get(index1).setWaiting_Time(Q1.get(index1).getTurn_Round()-Q1.get(index1).getCPU_burst());
                   Q1.get(index1).setRespons_Time(Q1.get(index1).getStrat_Time()-Q1.get(index1).getArival_Time());

                   clock+=Q1.get(index1).getCPU_burst();
                 }else if(Q2.size()!=0 && index2<Q2.size()) 
                       {
                           
                         if(clock>=Q2.get(index2).getArival_Time()&& Q2.get(index2).getTermination_Time()==0){
                              if(CPUChart.size()>0){
                                if( CPUChart.get(CPUChart.size()-1).equalsIgnoreCase(Q2.get(index2).getProc_ID())==false)
                                {
                                    CPUChart.add( Q2.get(index2).getProc_ID());
                                }
                              }else
                               CPUChart.add( Q2.get(index2).getProc_ID());
                                    if(c == 0 ) Q2.get(index2).setStrat_Time(clock);
                                    if(c == Q2.get(index2).getCPU_burst()) 
                                      { 
                                        Q2.get(index2).setTermination_Time(clock);
                                        Q2.get(index2).setTurn_Round(Q2.get(index2).getTermination_Time()-Q2.get(index2).getArival_Time());
                                        Q2.get(index2).setWaiting_Time(Q2.get(index2).getTurn_Round()-Q2.get(index2).getCPU_burst());
                                        Q2.get(index2).setRespons_Time(Q2.get(index2).getStrat_Time()-Q2.get(index2).getArival_Time());
                                        index2++;clock--;c=0;
                                        
                                      }
                                else
                                    c++;
                             clock++;}
                           else
                            clock++;}
                 else
                      clock++;

           }while(!isReadyQueuesEmpty( Q1, Q2));
      
             
               
        }
      public static double getAverageTAT(ProcessAttribt [] tempArray)
    {
        int sum=0;
        for(int i=0;i<tempArray.length;i++)
            sum+=tempArray[i].getTurn_Round();
            
        return (double)sum/tempArray.length;
        
    }
       public static double getAverageWT(ProcessAttribt [] tempArray)
    {
        int sum=0;
        for(int i=0;i<tempArray.length;i++)
            sum+=tempArray[i].getWaiting_Time();
            
        return (double)sum/tempArray.length;
        
    }
        public static double getAverageRT(ProcessAttribt [] tempArray)
    {
        int sum=0;
        for(int i=0;i<tempArray.length;i++)
            sum+=tempArray[i].getRespons_Time();
            
        return (double)sum/tempArray.length;
        
    }
     
       public static boolean isReadyQueuesEmpty(ArrayList<ProcessAttribt> Q1,ArrayList<ProcessAttribt> Q2)
    {
        boolean f=true;
        for(int i=0;i<Q1.size()&& f;i++)
            if (Q1.get(i).getTermination_Time()==0) 
                  f= false;
               
        for(int i=0;i<Q2.size()&& f;i++)
            if (Q2.get(i).getTermination_Time()==0) 
                f= false;
        return f;
    }
   public static int Find_SJF(int clock,ArrayList<ProcessAttribt> Q1)
    {
        int SJ_Index=-1;
         for(int i=0;i<Q1.size();i++)
            if(clock>=Q1.get(i).getArival_Time()&&Q1.get(i).getTermination_Time()==0)
                SJ_Index=i;
       
        if(SJ_Index==-1) return -1;
        for(int i=0;i<Q1.size();i++)
        if((Q1.get(i).getCPU_burst()<Q1.get(SJ_Index).getCPU_burst())&&( Q1.get(i).getTermination_Time()==0)&&(clock>=Q1.get(i).getArival_Time()))
            SJ_Index=i;
       return SJ_Index;
    }
    public static void initilizeQ1AndQ2(ProcessAttribt [] tempArray,ArrayList<ProcessAttribt> Q1,ArrayList<ProcessAttribt> Q2)
    {
       
        for(int i=0;i<tempArray.length;i++)
        {
            if(tempArray[i].getProc_Priority()==1)
                   Q1.add(tempArray[i]);
            else
                   Q2.add(tempArray[i]);
           
        }
                        Collections.sort(Q2, new Comparator<ProcessAttribt>() {
                     public int compare(ProcessAttribt s1, ProcessAttribt s2) {
                                          if(s1.getArival_Time()<s2.getArival_Time())
                                               return -1;
                                          else
                                              return 1;

                                          } });
    }
    public  static int  mainMenu()
    { 
        Scanner sc=new Scanner(System.in);
         int selectedOption;//number of process or tasks
         System.out.println("--Main Menu--");
         System.out.println("1)  Enter process information");
         System.out.println("2)  Report detailed information about each process");
         System.out.println("3)  Report the average turnaround time, waiting time, and response time");
         System.out.println("4)  Exit the program");
         try{
         selectedOption=sc.nextInt();
         
        
         while (!(selectedOption >=1 && selectedOption<=4))
                {
                     System.out.println("Your Selection not correct please try again :");
                     selectedOption=sc.nextInt();
                }
          return selectedOption;
         }
          catch(InputMismatchException e)
         {
             return 0;
         }
    }
         
     public static void screenShowDeails(ProcessAttribt [] tempArray)
    {
      StringBuilder sb=new StringBuilder();   
      sb.append(String.format("%-12s", "proc-ID"));
      sb.append(String.format("%-12s",  "Priority"));
      sb.append(String.format("%-12s",  "CPU-Burst"));
      sb.append(String.format("%-12s",  "Ariv-Time"));   
      sb.append(String.format("%-12s",  "Start-Time"));
      sb.append( String.format("%-12s", "Term-Time"));  
      sb.append(String.format("%-12s",  "Turn-Round"));
      sb.append(String.format("%-12s",  "wait-Time"));
      sb.append( String.format("%-12s", "Resp-Time"));   
       System.out.println(sb.toString());
       System.out.println("------------------------------------------------------");
               for(int i=0;i<tempArray.length;i++) 
                     System.out.println(tempArray[i].toString());
            
                toTextFileShowDeails(tempArray);
    }
     public static void toTextFileShowDeails(ProcessAttribt [] tempArray)
    {
      File outputFile = new File("Report1.txt");
      try{
            PrintStream stream = new PrintStream(outputFile);
            PrintStream console = System.out;
            System.setOut(stream);
            StringBuilder sb=new StringBuilder();   
            sb.append(String.format("%-12s", "proc-ID"));
            sb.append(String.format("%-12s",  "Priority"));
            sb.append(String.format("%-12s",  "CPU-Burst"));
            sb.append(String.format("%-12s",  "Ariv-Time"));   
            sb.append(String.format("%-12s",  "Start-Time"));
            sb.append( String.format("%-12s", "Term-Time"));  
            sb.append(String.format("%-12s",  "Turn-Round"));
            sb.append(String.format("%-12s",  "wait-Time"));
            sb.append( String.format("%-12s", "Resp-Time"));   
            System.out.println(sb.toString());
            System.out.println("------------------------------------------------------");
               for(int i=0;i<tempArray.length;i++) 
                     System.out.println(tempArray[i].toString());
            System.setOut(console);
       }catch (IOException e)
       {
           e.printStackTrace();
      }
   }
     
      public static void screenShowDeails2(double averageTAT,double averageWT,double averageRT,ProcessAttribt [] tempArray)
    {
      StringBuilder sb=new StringBuilder();   
      sb.append(String.format("%-12s", "proc-ID"));
      sb.append(String.format("%-12s",  "Priority"));
      sb.append(String.format("%-12s",  "CPU-Burst"));
      sb.append(String.format("%-12s",  "Ariv-Time"));   
      sb.append(String.format("%-12s",  "Start-Time"));
      sb.append( String.format("%-12s", "Term-Time"));  
      sb.append(String.format("%-12s",  "Turn-Round"));
      sb.append(String.format("%-12s",  "wait-Time"));
      sb.append( String.format("%-12s", "Resp-Time"));   
       System.out.println(sb.toString());
       System.out.println("------------------------------------------------------");
               for(int i=0;i<tempArray.length;i++) 
                     System.out.println(tempArray[i].toString());
            System.out.println("Average TAT = "+ averageTAT);
             System.out.println("Average WT = "+ averageWT);
             System.out.println("Average RT = "+ averageRT);

                toTextFileShowDeails2(averageTAT,averageWT,averageRT,tempArray);
    }
     public static void toTextFileShowDeails2(double averageTAT,double averageWT,double averageRT,ProcessAttribt [] tempArray)
    {
      File outputFile = new File("Report2.txt");
      try{
            PrintStream stream = new PrintStream(outputFile);
            PrintStream console = System.out;
            System.setOut(stream);
            StringBuilder sb=new StringBuilder();   
            sb.append(String.format("%-12s", "proc-ID"));
            sb.append(String.format("%-12s",  "Priority"));
            sb.append(String.format("%-12s",  "CPU-Burst"));
            sb.append(String.format("%-12s",  "Ariv-Time"));   
            sb.append(String.format("%-12s",  "Start-Time"));
            sb.append( String.format("%-12s", "Term-Time"));  
            sb.append(String.format("%-12s",  "Turn-Round"));
            sb.append(String.format("%-12s",  "wait-Time"));
            sb.append( String.format("%-12s", "Resp-Time"));   
            System.out.println(sb.toString());
            System.out.println("------------------------------------------------------");
               for(int i=0;i<tempArray.length;i++) 
                     System.out.println(tempArray[i].toString());
            System.out.println("Average TAT = "+ averageTAT);
            System.out.println("Average WT = "+ averageWT);
            System.out.println("Average RT = "+ averageRT);
            System.setOut(console);
       }catch (IOException e)
       {
           e.printStackTrace();
      }
   }
     
     public static int readDigit()
     {
         try{
              Scanner sc=new Scanner(System.in);
              return sc.nextInt();
         }
         catch (InputMismatchException e)
         {
             return -1;
         }
         
     }
     

                    
 }
 

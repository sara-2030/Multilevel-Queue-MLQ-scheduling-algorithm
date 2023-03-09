
package mlqproj;

public class ProcessAttribt
{
  private String proc_ID;
   private int Proc_Priority;
   private int arival_Time;
   private int CPU_burst;
   private int Strat_Time;
   private int Termination_Time;
   private int Turn_Round;
   private int waiting_Time;
   private int Respons_Time;
   
   public ProcessAttribt(String pid,int p,int b,int r)
   {
        proc_ID=pid;
        Proc_Priority=p;
        arival_Time=r;
        CPU_burst=b;
        Strat_Time=0;
        Termination_Time=0;
        Turn_Round=0;
        waiting_Time=0;
        Respons_Time=0;
   }
   public String getProc_ID(){
       return proc_ID;
   }
   public int getProc_Priority(){
       return Proc_Priority;
   }
   
    public int getArival_Time(){
       return arival_Time;
   }
   
   public int getCPU_burst(){
       return CPU_burst;
   }
   
   public int getStrat_Time(){
       return Strat_Time;
   }
   
    public int getTermination_Time() {
       return Termination_Time;
   }
   public int getTurn_Round(){
       return Turn_Round;
   }
  public int getWaiting_Time(){
       return waiting_Time;
   }
   public int getRespons_Time(){
       return Respons_Time;
   }
  
 
   
   
   public void setProc_ID(String ps){
       proc_ID=ps;
   }
   public void setProc_Priority(int pp){
       Proc_Priority=pp;
   }
   
    public void setArival_Time(int at){
       arival_Time=at;
   }
   
   public void  setCPU_burst(int b){
       CPU_burst=b;
   }
   
   public void setStrat_Time(int st){
       Strat_Time=st;
   }
   
    public void setTermination_Time(int tt) {
      Termination_Time=tt;
   }
   public void setTurn_Round(int tr){
       Turn_Round=tr;
   }
  public void setWaiting_Time(int wt){
       waiting_Time=wt;
   }
   public void setRespons_Time(int rt){
       Respons_Time=rt;
   }
    
   
     @Override
     public String toString()
    {
      StringBuilder sb=new StringBuilder();   
      sb.append(String.format("%-12s", proc_ID));
      sb.append(String.format("%-12d", Proc_Priority));
      sb.append(String.format("%-12d", CPU_burst));
      sb.append(String.format("%-12d", arival_Time));   
      sb.append(String.format("%-12d", Strat_Time));
      sb.append( String.format("%-12d", Termination_Time));  
      sb.append(String.format("%-12d", Turn_Round));
      sb.append(String.format("%-12d", waiting_Time));
      sb.append( String.format("%-12d", Respons_Time));   
    
        return sb.toString();
              
    }
}

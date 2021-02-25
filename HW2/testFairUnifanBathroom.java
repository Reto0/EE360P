import java.util.Random;
import java.util.Scanner;

public class testFairUnifanBathroom implements Runnable {
    Random rand = new Random();
    final static int SIZE = 10;
    final FairUnifanBathroom br;

    public testFairUnifanBathroom(FairUnifanBathroom br) {
		this.br = br;
	}

    public void run() {
        String type = ((rand.nextInt(1000)%2 == 0) ? "UT" : "OU"); //0 is UT, 1 is OU
        int wait = rand.nextInt(100);
        try {
            if(type == "UT"){
                // System.out.println("Thread " + Thread.currentThread().getId() + " is entering bathroom UT");
                br.enterBathroomUT();
                Thread.sleep(wait);
                br.leaveBathroomUT();
                // System.out.println("Thread " + Thread.currentThread().getId() + " has left bathroom UT");
                
            }
		    else{
                // System.out.println("Thread " + Thread.currentThread().getId() + " is entering bathroom OU");
                br.enterBathroomOU();
                Thread.sleep(wait);
                br.leaveBathroomOU();
                // System.out.println("Thread " + Thread.currentThread().getId() + " has left bathroom OU");
            }
		} 
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

    public static void main(String[] args) {
        Random rand = new Random();
		FairUnifanBathroom br = new FairUnifanBathroom(); //limted to 4 people
		Thread[] t = new Thread[SIZE];
		
		for (int i = 0; i < SIZE; ++i) {
			t[i] = new Thread(new testFairUnifanBathroom(br));
		}
		
		for (int i = 0; i < SIZE; ++i) {
			t[i].start();
            try {
				Thread.sleep(rand.nextInt(50));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        for(int i = 0; i < SIZE; ++i){
            try {
				t[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("check? (y)");
        String inp = sc.nextLine();
        String lines[] = new String[3*SIZE];
        if(inp.equals("y")){
            for(int i = 0; i < 3*SIZE; i++){
                inp = sc.nextLine();
                lines[i] = inp;
            }
            int enter = 0;
            int wait = 0;
            int status = 0;
            int space = 4;
            for(int i = 0; i < 3*SIZE; i++){
                String parse[] = lines[i].split(" ");
                String type = parse[0].substring(0,1);
                if(type.equals("W")){
                    if(wait < Integer.parseInt(parse[2])){
                        wait = Integer.parseInt(parse[2]);
                    }
                    else{
                        System.out.println("WRONG 0" + " int is: " + Integer.parseInt(parse[2]));
                        return;
                    }
                }
                else if(type.equals("E")){
                    if(enter < Integer.parseInt(parse[2])){
                        enter = Integer.parseInt(parse[2]);
                    }
                    else{
                        System.out.println("WRONG 1");
                        return;
                    }
                    type = parse[0].substring(1,3);
                    int s = 0;
                    if(type.equals("OU"))
                        s = 2;
                    else
                        s = 1;
                    if(status != 0 && status != s || space == 0){
                        System.out.println("WRONG 2");
                        return;
                    }
                    if(s == 1){
                        status = 1;
                        space--;
                    }
                    if(s == 2){
                        status = 2;
                        space--;
                    }
                }
                else if(type.equals("S")){
                    type = parse[4];
                    int s = 0;
                    if(type.equals("OU"))
                        s = 2;
                    else
                        s = 1;
                    if(space == 4 || s != status){
                        System.out.println("WRONG");
                        return;
                    }
                    space++;
                    if(space == 4)
                        status = 0;
                }
            }
            System.out.println("all good");
        }
        sc.close();
    }
}

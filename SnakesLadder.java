import java.io.*;
import java.util.*;

public class SnakesLadder extends AbstractSnakeLadders 
{
	
	int N, M;
	int snakes[];
	int ladders[];
	int min_moves[];
	int reverse_min_moves[];
	int reverse_snakes[];
	int reverse_ladders[];
	int best_snake[];
	ArrayList<int[]> only_ladders = new ArrayList<>();
	
	public SnakesLadder(String name)throws Exception{
		File file = new File(name);
		BufferedReader br = new BufferedReader(new FileReader(file));
		N = Integer.parseInt(br.readLine());
        
        M = Integer.parseInt(br.readLine());

	    snakes = new int[N];
		ladders = new int[N];
		
		
	    for (int i = 0; i < N; i++){
			snakes[i] = -1;
			ladders[i] = -1;
		}

		reverse_snakes=new int[N+1];
		reverse_ladders=new int[N+1];
		for (int i = 0; i < N; i++){reverse_ladders[i]=-1;reverse_snakes[i]=-1;}

		for(int i=0;i<M;i++){
            String e = br.readLine();
            StringTokenizer st = new StringTokenizer(e);
            int source = Integer.parseInt(st.nextToken());
            int destination = Integer.parseInt(st.nextToken());

			if(source<destination){
				ladders[source] = destination;
				reverse_snakes[destination] = source;
				int[] pi = {source,destination,0,0};
				only_ladders.add(pi);
			}
			else{
				snakes[source] = destination;
				reverse_ladders[destination] = source;
			}
        }

		min_moves = new int[N+1];
		for (int i = 0; i < N+1; i++){min_moves[i]=-1;}

		reverse_min_moves = new int [N+1];
		for (int i = 0; i < N; i++){reverse_min_moves[i]=-1;}

		Queue<Integer> q = new LinkedList<Integer>();
		q.add(0);
		int [] visit = new int[N+1];
		for (int i = 0; i < N+1; i++){visit[i] = 0;}
		int steps = 0;
		while (!q.isEmpty()){
			int itr = q.size();
			steps++;

			for(int j=0; j<itr; j++){
				int x=q.remove();
				for(int i = 1; i<7; i++){
					int new_one = x+i;
					if(new_one==N && visit[N]==0){min_moves[N]= steps;visit[N]=1;}
					while(true){
						if ( new_one < N && ladders[new_one]!=-1){new_one = ladders[new_one];}
						else if (new_one <N && snakes[new_one]!=-1){new_one = snakes[new_one];}
						else{break;}
					}
					if(new_one==N && visit[N]==0){min_moves[N]= steps;visit[N]=1;}
					if(new_one<N && visit[new_one]==0){q.add(new_one); visit[new_one]=1;min_moves[new_one]=steps;}
				}
			}
		}	


		Queue<Integer> qu = new LinkedList<Integer>();
		qu.add(N);
		visit = new int[N+1];
		for (int i = 0; i < N+1; i++){visit[i] = 0;}
		steps = 0;
		while (!qu.isEmpty()){
			int itr = qu.size();
			steps++;

			for(int j=0; j<itr; j++){
				int x=qu.remove();
				for(int i = 1; i<7; i++){
					int new_one = x-i;
					// if(new_one==1 && visit[1]==0){min_moves[1]= steps;visit[1]=1;}
					while(true){
						if (new_one>0 && new_one !=N && reverse_ladders[new_one]!=-1){new_one = reverse_ladders[new_one];}
						else if (new_one>0 && new_one !=N && reverse_snakes[new_one]!=-1){new_one = reverse_snakes[new_one];}
						else{break;}
					}
					if(new_one>0 && visit[new_one]==0){qu.add(new_one); visit[new_one]=1;reverse_min_moves[new_one]=steps;}
				}
			}
		}	

		// for(int i=0; i<N+1; i++){
		// 	System.out.print(reverse_min_moves[i] + " ");
		// }

		for (int i=0; i<only_ladders.size(); i++){
			only_ladders.get(i)[2]=min_moves[only_ladders.get(i)[1]];
			only_ladders.get(i)[3]=reverse_min_moves[only_ladders.get(i)[0]];
		}
		// for(int i= 0; i<only_ladders.size();i++){
		// 	for (int j=0; j<4; j++){System.out.print(only_ladders.get(i)[j]+" ");}
		// 	System.out.println("");
		// }
		best_snake= new int[2];
		best_snake[0]=-1;best_snake[1]=-1;
		int m_m_b_s=0;
		for (int i=0; i<only_ladders.size(); i++){
			int[] xi = only_ladders.get(i);
			for (int j=0; j<only_ladders.size();j++){
				int[] ti = only_ladders.get(j);
				if(xi[2]==-1 || xi[3]==-1 || ti[2]==-1 || ti[3]==-1){continue;}
				if(xi[1]>ti[0]){
					if(m_m_b_s==0 || m_m_b_s>xi[2]+ti[3]){m_m_b_s=xi[2]+ti[3];best_snake[0]=xi[1];best_snake[1]=ti[0];}
				}
			}
		}
		// System.out.println(best_snake[0] + " bs  " + best_snake[1]);
		


	}

	// public int OptimalMoves(int st, int en){
	// 	Queue<Integer> q = new LinkedList<Integer>();
	// 	q.add(st-1);
	// 	int [] visit = new int[N];
	// 	for (int i = st; i < en; i++){visit[i] = 0;}
	// 	int steps = 0;
	// 	while (!q.isEmpty()){
	// 		int itr = q.size();
	// 		steps++;
	// 		for(int j=0; j<itr; j++){
	// 			int x=q.remove();
	// 			for(int i = 1; i<7; i++){
	// 				int new_one = x+i;
	// 				if(new_one==en && new_one==N){return steps;}
	// 				while(true){
	// 					if (ladders[new_one]!=-1){new_one = ladders[new_one];}
	// 					else if (snakes[new_one]!=-1){new_one = snakes[new_one];}
	// 					else{break;}
	// 				}
	// 				if(new_one==en){return steps;}
	// 				if(visit[new_one]==0){q.add(new_one); visit[new_one]=1;}
	// 			}
	// 	}
	// }
	// 	return -1;
	// }
    
	public int OptimalMoves(){
		/* Complete this function and return the minimum number of moves required to win the game. */
	// 	Queue<Integer> q = new LinkedList<Integer>();
	// 	q.add(0);
	// 	int [] visit = new int[N];
	// 	for (int i = 0; i < N; i++){visit[i] = 0;}
	// 	int steps = 0;
	// 	while (!q.isEmpty()){
	// 		int itr = q.size();
	// 		steps++;
	// 		for(int j=0; j<itr; j++){
	// 			int x=q.remove();
	// 			for(int i = 1; i<7; i++){
	// 				int new_one = x+i;
	// 				if(new_one==N){return steps;}
	// 				while(true){
	// 					if (ladders[new_one]!=-1){new_one = ladders[new_one];}
	// 					else if (snakes[new_one]!=-1){new_one = snakes[new_one];}
	// 					else{break;}
	// 				}
	// 				if(new_one==N){return steps;}
	// 				if(visit[new_one]==0){q.add(new_one); visit[new_one]=1;}
	// 			}
	// 	}
	// }
	// 	return -1;
	return min_moves[N];
	}

	public int min_move(int n){return min_moves[n];}
	public int reverse_min_move(int n){return reverse_min_moves[n];}


	public int Query(int x, int y)
	{
		/* Complete this function and 
			return +1 if adding a snake/ladder from x to y improves the optimal solution, 
			else return -1. */
			// System.out.println(x);
			// System.out.println(y);
			// if(OptimalMoves(1,x) + OptimalMoves(y+1,N) + 1 < OptimalMoves(1,N)){return 1;}
			if(min_move(x) + reverse_min_move(y)<min_move(N)){return 1;}
			return -1;
	}

	public int[] FindBestNewSnake()
	{
		int result[] = {1, 10};
		/* Complete this function and 
			return (x, y) i.e the position of snake if adding it increases the optimal solution by largest value,
			if no such snake exists, return (-1, -1) */

		return best_snake;
	}

	// public static void main(String[] args) throws Exception {
	// 	SnakesLadder sl = new SnakesLadder("input.txt");
	// 	// System.out.println(sl.OptimalMoves());
	// }
   
}
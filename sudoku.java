import java.util.*;

public class sudoku
{
//from here starts solve sudoku
    public static boolean isValid(int [][] board,int po,int i,int j)
    {
        //char c='0'+po;
        int c=po;
        for(int m=0;m<9;m++){
            if(board[i][m]==(c))
                return false;
        }
        for(int m=0;m<9;m++){
            if(board[m][j]==(c))
                return false;
        }
        int sub_i=3*(i/3);
        int sub_j=3*(j/3);
        for(int m=0;m<3;m++){
            for(int n=0;n<3;n++){
                if(board[sub_i+m][sub_j+n]==(c))
                    return false;
            }
        }
        return true;
    }
    public static boolean solve (int [][] board,int i,int j)
    {
        int ni,nj;//cout<<i<<"  "<<j<<endl;
        if(i==9){
                /*for(int m=0;m<9;m++){
            for(int n=0;n<9;n++){
                cout<<board[m][n]<<" ";
            }
        cout<<endl;
        }*/
            return true;
        }
        if(j==(9-1)){
            ni=i+1;nj=0;
        }
        else{
            nj=j+1;ni=i;
        }

        if(board[i][j]==0){
            for(int po=1;po<=9;po++){
                if(isValid(board,po,i,j)){
                         int c=po;
                    board[i][j]=c;
                    if(solve(board,ni,nj))
                        return true;
                    board[i][j]=0;
                }
            }
        }
        else
        {
            if(solve(board,ni,nj))
                        return true;
        }
        return false;
    }


    //from here starts generating main diagonal grid
    //check if n is valid in the submatrix diagonal
    public static boolean isValDia(int [][] board,int n,int i,int j)
    {
        for(int a=0;a<3;a++){
            for(int b=0;b<3;b++){
                    if(board[a+i][b+j]==n)
                    return false;
            }
        }
        return true;
    }
    //initialize each diagonal
    public static void gen_dia_code(int [][] board,int i,int j)
    {
        for(int a=0;a<3;a++){
            for(int b=0;b<3;b++){
                    boolean check=true;
                    while(check){
                int n=(int)(Math.random()*(9-1+1)+1);
                    if(isValDia(board,n,i,j)){
                        board[a+i][b+j]=n;check=false;}
                    }
            }
        }
    }
    //initiate main diagonal
    public static void gen_dia(int [][] board)
    {
        int i=0,j=0;
        //1st dia
        gen_dia_code(board,i,j);
        i=3;j=3;
        gen_dia_code(board,i,j);
         i=6;j=6;
        gen_dia_code(board,i,j);
    }

    //after removing each element check if the there is unique solution for this sudoku
    public static void solve_check (int [][] board,int i,int j,int []cnt_unique)
    {
        int ni,nj;//cout<<i<<"  "<<j<<endl;
        if(i==9)
        {
        cnt_unique[0]++;
        }
        else
        {
                if(j==(9-1))
                {
                    ni=i+1;nj=0;
                }
                else
                {
                    nj=j+1;ni=i;
                }
                if(board[i][j]==0)
                {
                    for(int po=1;po<=9;po++)
                    {
                        if(isValid(board,po,i,j))
                        {
                                 int c=po;
                            board[i][j]=c;
                            //if(solve(board,ni,nj))
                                //return true;
                                solve_check(board,ni,nj,cnt_unique);

                            board[i][j]=0;
                        }
                    }
                }
                else
                 solve_check(board,ni,nj,cnt_unique);

        }
    }

    public static void gen_uns_sud(int [][]board,int filled)
    {
        int [][]try_board=new int[9][9];
        int emp=81-filled;
        int i=0;//a=0,b=0;
        int []cnt_unique=new int[1];
        while(i<emp)
        {
            int ran_r=(int)(Math.random()*(8-0+1)+0);
            int ran_c=(int)(Math.random()*(8-0+1)+0);
            if(board[ran_r][ran_c]==0)
                continue;
            int val=board[ran_r][ran_c];
            board[ran_r][ran_c]=0;
            cnt_unique[0]=0;
            solve_check(board,0,0,cnt_unique);
            if(cnt_unique[0]>1){
                    board[ran_r][ran_c]=val;
                continue;
            }
            //cout<<cnt_unique<<endl<<endl;
            i++;
        }
        
        
    }

    public static void display(int [][]board)
    {
        for(int m=0;m<9;m++)
        {
            for(int n=0;n<9;n++)
            {
                System.out.print(board[m][n]+" ");
                if(n==2 || n==5 )
                    System.out.print("||");
            }

            System.out.print("\n");
            if(m==2 || m==5)
                System.out.print("=====================\n");
        }
    }
    public static void main(String []args)
    {
        Scanner sc=new Scanner(System.in);
        int [][] board=new int [9][9];int [][]solution=new int[9][9];

     
        //generate the element in the 3 principal diagonal grids
        gen_dia(board);
        
        
        int ni=0,nj=0;

        //solve the sudoku using backtracking
        solve(board,ni,nj);
        
        //store the solution
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                solution[i][j]=board[i][j];
            }
        }


        System.out.println("Enter 1 for Easy, 2 for Medium , 3 for Hard\n");
        int choice=sc.nextInt();
        if(choice==1){
            gen_uns_sud(board,43);
        }
        else if(choice==2){
            gen_uns_sud(board,38);
        }
        else{
            gen_uns_sud(board,31);
        }

        //check if unsolved sud is unique
    int cnt=0;
    //solve_check(board,0,0,cnt);

    System.out.println("\n\nSolve the sudoku given below:\n\n");
    display(board);

    System.out.println("\n\nInput your sudoku for checking");
    int [][] input=new int [9][9];
    for(int i=0;i<9;i++)
    {
        for(int j=0;j<9;j++)
        {
            input[i][j]=sc.nextInt();
        }
    }

    //using linear search to check whether the input sudoku is same as the solved sudoku generated
    boolean check=true;
    for(int i=0;i<9;i++)
    {
        for(int j=0;j<9;j++)
        {
            if(input[i][j]!=solution[i][j])
            {
                check=false;break;
            }
        }
        if(!check)
            break;
    }
    if(check)
        System.out.println("Congrats!You solved the sudoku correctly.");
    else
        System.out.println("Sorry!Your solution is not correct.");

    }
}


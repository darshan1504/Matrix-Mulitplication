
import java.io.*;
class Matrix
{
     int n,A[][];
     Matrix a,b,c,d;
     Matrix(int row)
     {
           n=row;
           A=new int[n][n];
     }
     Matrix(int[][]arr)
     {
           this.n=arr[0].length;
           A=new int[n][n];
           for(int i=0;i<n;i++)
                for(int j=0;j<n;j++)
                     A[i][j]=arr[i][j];
     }
     public void getFourSmallMatrices()
     {
           a=new Matrix(this.n/2);
           b=new Matrix(this.n/2);
           c=new Matrix(this.n/2);
           d=new Matrix(this.n/2);
           for(int i=0;i<a.n;i++)
                for(int j=0;j<a.n;j++)
                     a.A[i][j]=this.A[i][j];
           for(int i=a.n;i<2*a.n;i++)
                for(int j=0;j<a.n;j++)
                     c.A[i-a.n][j]=A[i][j];
           for(int i=0;i<a.n;i++)
                for(int j=a.n;j<2*a.n;j++)
                     b.A[i][j-a.n]=A[i][j];
           for(int i=a.n;i<2*a.n;i++)
                for(int j=a.n;j<2*a.n;j++)
                     d.A[i-a.n][j-a.n]=A[i][j];
     }
     public static Matrix add(Matrix m1,Matrix m2)
     {
           Matrix addMatrix=new Matrix(m1.n);
           for(int i=0;i<addMatrix.n;i++)
                for(int j=0;j<addMatrix.n;j++)
                     addMatrix.A[i][j]=m1.A[i][j]+m2.A[i][j];
           return addMatrix;
     }
     public static Matrix getBigMatrix(Matrix a,Matrix b,Matrix c,Matrix d)
     {
           Matrix m=new Matrix(a.n*2);
           for(int i=0;i<a.n;i++)
                for(int j=0;j<a.n;j++)
                     m.A[i][j]=a.A[i][j];
           for(int i=a.n;i<2*a.n;i++)
                for(int j=0;j<a.n;j++)
                     m.A[i][j]=c.A[i-a.n][j];
           for(int i=0;i<a.n;i++)
                for(int j=a.n;j<2*a.n;j++)
                     m.A[i][j]=b.A[i][j-a.n];
           for(int i=a.n;i<2*a.n;i++)
                for(int j=a.n;j<2*a.n;j++)
                     m.A[i][j]=d.A[i-a.n][j-a.n];
           return m;
     }   
     public void printMatrix()
     {
           for(int i=0;i<n;i++)
           {
                for(int j=0;j<n;j++)
                     System.out.print(A[i][j]+"\t");
                System.out.println();
           }
     }
}   

class Main
{
     public static void main(String args[])
     {
           try
           {
                BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter the rows/columns of the matrix     : ");
                int n=Integer.parseInt(br.readLine());
                System.out.println("Enter the data of the first matrix : ");
                int arr1[][]=new int[n][n];
                int arr2[][]=new int[n][n];
                for(int i=0;i<n;i++)
                {
                     for(int j=0;j<n;j++)
                     {
                           System.out.print("Enter the data Item for Array1["+i+"]["+j+"] : ");
                           arr1[i][j]=Integer.parseInt(br.readLine());
                     }
                }
                System.out.println("Enter the data of the second matrix : ");
                for(int i=0;i<n;i++)
                {
                     for(int j=0;j<n;j++)
                     {
                           System.out.print("Enter the data Item for Array2["+i+"]["+j+"] : ");
                           arr2[i][j]=Integer.parseInt(br.readLine());
                     }
                }
                Matrix ans=SquareMatrixMultiplyRecursive(new Matrix(arr1),new Matrix(arr2),n);
                ans.printMatrix();
           }
           catch(IOException e){System.out.println("you got an exception : "+e);}
     }
    
     public static Matrix SquareMatrixMultiplyRecursive(Matrix arr1,Matrix arr2,int n)
     {
           Matrix ans=new Matrix(n);
           if(n==1)
           {
                ans.A[0][0]=arr1.A[0][0]*arr2.A[0][0];
                return ans;
           }
           arr1.getFourSmallMatrices();
           arr2.getFourSmallMatrices();
          
          ans.a=Matrix.add(SquareMatrixMultiplyRecursive(arr1.a,arr2.a,n/2),SquareMatrixMultiplyRecursive(arr1.b,arr2.c,n/2));
          ans.b=Matrix.add(SquareMatrixMultiplyRecursive(arr1.a,arr2.b,n/2),SquareMatrixMultiplyRecursive(arr1.b,arr2.d,n/2));
          ans.c=Matrix.add(SquareMatrixMultiplyRecursive(arr1.c,arr2.a,n/2),SquareMatrixMultiplyRecursive(arr1.d,arr2.c,n/2));
          ans.d=Matrix.add(SquareMatrixMultiplyRecursive(arr1.c,arr2.b,n/2),SquareMatrixMultiplyRecursive(arr1.d,arr2.d,n/2));
          
           ans=Matrix.getBigMatrix(ans.a,ans.b,ans.c,ans.d);
           return ans;
     }
}
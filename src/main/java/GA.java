import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

public class GA {

    private int scale;// 种群规模
    private int cityNum; // 城市数量，染色体长度
    private int MAX_GEN; // 运行代数
    private int[][] distance; // 距离矩阵
    private int bestT;// 最佳出现代数
    private int bestLength; // 最佳长度
    private int[] bestTour; // 最佳路径

    // 初始种群，父代种群，行数表示种群规模，一行代表一个个体，即染色体，列表示染色体基因片段
    private int[][] oldPopulation;
    private int[][] newPopulation;// 新的种群，子代种群
    private int[] fitness;// 种群适应度，表示种群中各个个体的适应度

    private float[] Pi;// 种群中各个个体的累计概率
    private float Pc;// 交叉概率
    private float Pm;// 变异概率
    private int t;// 当前代数

    private Random random;

    public GA() {

    }

    /**
     * constructor of GA
     *
     * @param s
     *            种群规模
     * @param n
     *            城市数量
     * @param g
     *            运行代数
     * @param c
     *            交叉率
     * @param m
     *            变异率
     *
     **/
    public GA(int s, int n, int g, float c, float m) {
        scale = s;
        cityNum = n;
        MAX_GEN = g;
        Pc = c;
        Pm = m;
    }

    // 给编译器一条指令，告诉它对被批注的代码元素内部的某些警告保持静默
    @SuppressWarnings("resource")
    /**
     * 初始化GA算法类
     * @param filename 数据文件名，该文件存储所有城市节点坐标数据
     * @throws IOException
     */
    public void init(String filename) throws IOException {
        // 读取数据
        int[] x;
        int[] y;
        String strbuff;
        BufferedReader data = new BufferedReader(new InputStreamReader(
                new FileInputStream(filename)));
        distance = new int[cityNum][cityNum];
        x = new int[cityNum];
        y = new int[cityNum];
        for (int i = 0; i < cityNum; i++) {
            // 读取一行数据，数据格式1 6734 1453
            strbuff = data.readLine();
            // 字符分割
            String[] strcol = strbuff.split(" ");
            x[i] = Integer.valueOf(strcol[1]);// x坐标
            y[i] = Integer.valueOf(strcol[2]);// y坐标
        }
        // 计算距离矩阵
        // ，针对具体问题，距离计算方法也不一样，此处用的是att48作为案例，它有48个城市，距离计算方法为伪欧氏距离，最优值为10628
        for (int i = 0; i < cityNum - 1; i++) {
            distance[i][i] = 0; // 对角线为0
            for (int j = i + 1; j < cityNum; j++) {
                double rij = Math
                        .sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
                                * (y[i] - y[j])) / 10.0);
                // 四舍五入，取整
                int tij = (int) Math.round(rij);
                if (tij < rij) {
                    distance[i][j] = tij + 1;
                    distance[j][i] = distance[i][j];
                } else {
                    distance[i][j] = tij;
                    distance[j][i] = distance[i][j];
                }
            }
        }
        distance[cityNum - 1][cityNum - 1] = 0;

        bestLength = Integer.MAX_VALUE;
        bestTour = new int[cityNum + 1];
        bestT = 0;
        t = 0;

        newPopulation = new int[scale][cityNum];
        oldPopulation = new int[scale][cityNum];
        fitness = new int[scale];
        Pi = new float[scale];

        random = new Random(System.currentTimeMillis());
    }

    // 初始化种群
    void initGroup() {
        int i, j, k;
        for (k = 0; k < scale; k++)// 种群数
        {
            oldPopulation[k][0] = random.nextInt(65535) % cityNum;
            for (i = 1; i < cityNum;)// 染色体长度
            {
                oldPopulation[k][i] = random.nextInt(65535) % cityNum;
                for (j = 0; j < i; j++) {
                    if (oldPopulation[k][i] == oldPopulation[k][j]) {
                        break;
                    }
                }
                if (j == i) {
                    i++;
                }
            }
        }
    }

    public int evaluate(int[] chromosome) {
        // 0123
        int len = 0;
        // 染色体，起始城市,城市1,城市2...城市n
        for (int i = 1; i < cityNum; i++) {
            len += distance[chromosome[i - 1]][chromosome[i]];
        }
        // 城市n,起始城市
        len += distance[chromosome[cityNum - 1]][chromosome[0]];
        return len;
    }

    // 计算种群中各个个体的累积概率，前提是已经计算出各个个体的适应度fitness[max]，作为赌轮选择策略一部分，Pi[max]
    void countRate() {
        int k;
        double sumFitness = 0;// 适应度总和

        double[] tempf = new double[scale];

        for (k = 0; k < scale; k++) {
            tempf[k] = 1.0 / fitness[k];
            sumFitness += tempf[k];
        }

        Pi[0] = (float) (tempf[0] / sumFitness);
        for (k = 1; k < scale; k++) {
            Pi[k] = (float) (tempf[k] / sumFitness + Pi[k - 1]);
        }
    }

    // 挑选某代种群中适应度最高的个体，直接复制到子代中
    // 前提是已经计算出各个个体的适应度Fitness[max]
    public void selectBestGh() {
        int k, i, maxid;
        int maxevaluation;

        maxid = 0;
        maxevaluation = fitness[0];
        for (k = 1; k < scale; k++) {
            if (maxevaluation > fitness[k]) {
                maxevaluation = fitness[k];
                maxid = k;
            }
        }

        if (bestLength > maxevaluation) {
            bestLength = maxevaluation;
            bestT = t;// 最好的染色体出现的代数;
            for (i = 0; i < cityNum; i++) {
                bestTour[i] = oldPopulation[maxid][i];
            }
        }

        // 复制染色体，k表示新染色体在种群中的位置，kk表示旧的染色体在种群中的位置
        copyGh(0, maxid);// 将当代种群中适应度最高的染色体k复制到新种群中，排在第一位0
    }

    // 复制染色体，k表示新染色体在种群中的位置，kk表示旧的染色体在种群中的位置
    public void copyGh(int k, int kk) {
        int i;
        for (i = 0; i < cityNum; i++) {
            newPopulation[k][i] = oldPopulation[kk][i];
        }
    }

    // 赌轮选择策略挑选
    public void select() {
        int k, i, selectId;
        float ran1;
        for (k = 1; k < scale; k++) {
            ran1 = (float) (random.nextInt(65535) % 1000 / 1000.0);
            double sum = 0.0;
            // 产生方式
            for (i = 0; i < scale; i++) {
//                sum += Pi[i];
//                if (ran1 <= sum) {
//                    break;
//                }
                if (ran1 <= Pi[i]) {
                    break;
                }
            }
            selectId = i;
            copyGh(k, selectId);
        }
    }


    //进化函数，保留最好染色体不进行交叉变异
    public void evolution() {
        int k;
        // 挑选某代种群中适应度最高的个体
        selectBestGh();

        // 赌轮选择策略挑选scale-1个下一代个体
        select();

        float r;

        for (k = 1; k < scale-1; k = k + 2) {
            r = random.nextFloat();// /产生概率
            if (r < Pc) {
                OXCross1(k, k + 1);// 进行交叉
            } else {
                r = random.nextFloat();// 产生概率
                // 变异
                if (r < Pm) {
                    OnCVariation(k);
                }
                r = random.nextFloat();// /产生概率
                // 变异
                if (r < Pm) {
                    OnCVariation(k + 1);
                }
            }
        }
        if (k == scale-1)// 剩最后一个染色体没有交叉
        {
            r = random.nextFloat();// 产生概率
            if (r < Pm) {
                OnCVariation(k);
            }
        }
    }


    // 交叉算子,相同染色体交叉产生不同子代染色体
    public void OXCross1(int k1, int k2) {
        int i, j, k, flag;
        int ran1, ran2, temp;
        int[] Gh1 = new int[cityNum];
        int[] Gh2 = new int[cityNum];

        ran1 = random.nextInt(65535) % cityNum;
        ran2 = random.nextInt(65535) % cityNum;
        while (ran1 == ran2) {
            ran2 = random.nextInt(65535) % cityNum;
        }

        if (ran1 > ran2)// 确保ran1<ran2
        {
            temp = ran1;
            ran1 = ran2;
            ran2 = temp;
        }

        // 将染色体1中的第三部分移到染色体2的首部
        for (i = 0, j = ran2; j < cityNum; i++, j++) {
            Gh2[i] = newPopulation[k1][j];
        }

        flag = i;// 染色体2原基因开始位置

        for (k = 0, j = flag; j < cityNum;)// 染色体长度
        {
            Gh2[j] = newPopulation[k2][k++];
            for (i = 0; i < flag; i++) {
                if (Gh2[i] == Gh2[j]) {
                    break;
                }
            }
            if (i == flag) {
                j++;
            }
        }

        // 将染色体2中的第一部分移到染色体1的尾部
        flag = ran1;
        for (k = 0, j = 0; k < cityNum;)// 染色体长度
        {
            Gh1[j] = newPopulation[k1][k++];
            for (i = 0; i < flag; i++) {
                if (newPopulation[k2][i] == Gh1[j]) {
                    break;
                }
            }
            if (i == flag) {
                j++;
            }
        }

        flag = cityNum - ran1;

        for (i = 0, j = flag; j < cityNum; j++, i++) {
            Gh1[j] = newPopulation[k2][i];
        }

        for (i = 0; i < cityNum; i++) {
            newPopulation[k1][i] = Gh1[i];// 交叉完毕放回种群
            newPopulation[k2][i] = Gh2[i];// 交叉完毕放回种群
        }
    }

    // 多次对换变异算子
    public void OnCVariation(int k) {
        int ran1, ran2, temp;
        int count;// 对换次数

        count = random.nextInt(65535) % cityNum;

        for (int i = 0; i < count; i++) {

            ran1 = random.nextInt(65535) % cityNum;
            ran2 = random.nextInt(65535) % cityNum;
            while (ran1 == ran2) {
                ran2 = random.nextInt(65535) % cityNum;
            }
            temp = newPopulation[k][ran1];
            newPopulation[k][ran1] = newPopulation[k][ran2];
            newPopulation[k][ran2] = temp;
        }
    }

    public void solve() {
        int i;
        int k;

        // 初始化种群
        initGroup();
        // 计算初始化种群适应度，Fitness[max]
        for (k = 0; k < scale; k++) {
            fitness[k] = evaluate(oldPopulation[k]);
            // System.out.println(fitness[k]);
        }
        // 计算初始化种群中各个个体的累积概率，Pi[max]
        countRate();
//        System.out.println("初始种群...");
//        for (k = 0; k < scale; k++) {
//            for (i = 0; i < cityNum; i++) {
//                System.out.print(oldPopulation[k][i] + ",");
//            }
//            System.out.println();
//            System.out.println("----" + fitness[k] + " " + Pi[k]);
//        }

        for (t = 0; t < MAX_GEN; t++) {
            evolution();
            //evolution();
            // 将新种群newGroup复制到旧种群oldGroup中，准备下一代进化
            for (k = 0; k < scale; k++) {
                for (i = 0; i < cityNum; i++) {
                    oldPopulation[k][i] = newPopulation[k][i];
                }
            }
            // 计算种群适应度
            for (k = 0; k < scale; k++) {
                fitness[k] = evaluate(oldPopulation[k]);
            }
            // 计算种群中各个个体的累积概率
            countRate();
        }

//        System.out.println("最后种群...");
//        for (k = 0; k < scale; k++) {
//            for (i = 0; i < cityNum; i++) {
//                System.out.print(oldPopulation[k][i] + ",");
//            }
//            System.out.println();
//            System.out.println("---" + fitness[k] + " " + Pi[k]);
//        }

        System.out.print("最佳长度出现代数："+bestT+"  "+"最佳长度"+bestLength+"   "+"最佳路径：");
        for (i = 0; i < cityNum; i++) {
            System.out.print(bestTour[i] + ",");
        }
        System.out.println();

    }


    public int getBestLength() {
        return bestLength;
    }

    public int[] getBestTour() {
        return bestTour;
    }


    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Start....");
            GA ga = new GA(30, 48, 10000, 0.8f, 0.01f);
            ga.init("E:\\idea_workplace\\SwordOffer\\src\\main\\resources\\data.txt");
            ga.solve();
        }
    }
}


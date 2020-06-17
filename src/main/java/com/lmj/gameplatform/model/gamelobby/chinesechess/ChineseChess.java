package com.lmj.gameplatform.model.gamelobby.chinesechess;


class ChineseChess {
    private class Pieces
    {
        //规定 1车2马3象4士5将6炮7兵0无棋子
        public int type=0;
        //规定 0无色 1黑色 2红色
        public int color=0;
        public void setTypeColor(int type,int color) {
            this.type=type;
            this.color=color;
        }
        public void setTypeColor(Pieces pieces) {
            this.color=pieces.color;
            this.type=pieces.type;
        }
    }
    private Pieces[][] board=new Pieces[9][10];//横着有9个,竖着有10个
    //轮到谁下棋
    private int whoChess=0;//黑为1 红为2
    private String winner;
    //记录最后落子的位置
    private String lastPoint="";
    //初始化棋盘信息
    {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j]=new Pieces();
            }
        }
        replay();
    }
    //设置棋子信息
    public void replay() {
        //首先清理所有的棋子
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].setTypeColor(0, 0);
            }
        }
        //设置顶部和底部的棋子
        for (int i = 0; i < board.length; i++) {
            if(i<5) {
                board[i][0].setTypeColor(i+1, 1);
                board[i][9].setTypeColor(i+1, 2);
            }else {
                board[i][0].setTypeColor(board.length-i,1);
                board[i][9].setTypeColor(board.length-i,2);
            }
        }
        //设置炮
        board[1][2].setTypeColor(6, 1);
        board[1][7].setTypeColor(6, 2);
        board[7][2].setTypeColor(6, 1);
        board[7][7].setTypeColor(6, 2);
        //设置兵
        for (int i = 0; i < board[0].length; i+=2) {
            board[i][3].setTypeColor(7, 1);
            board[i][6].setTypeColor(7, 2);
        }
        //设置游戏信息
        whoChess=2;
        winner="无";
        lastPoint="";
    }

    //获取所有可落子位置,以"-"分割坐标,以","分割每个位置
    public String getAllCanChess(int who,int x,int y){
        if (who!=whoChess)return null;
        if(board[x][y].color!=whoChess)return null;
        StringBuffer buffer=new StringBuffer();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(canChess(x, y, i, j)) {
                    buffer.append(i+"-"+j+",");
                }
            }
        }
        return buffer.toString();
    }

    //获取棋盘信息,黑色不变,红色*10
    public String getGameData() {
        StringBuffer buffer=new StringBuffer();
        //第一部分为最后落子坐标
        buffer.append(lastPoint+":");
        //第二部分为轮到谁下棋
        if (whoChess==1){
            buffer.append("黑:");
        }else if (whoChess==2){
            buffer.append("红:");
        }else{
            buffer.append("无:");
        }
        //第三部分为谁胜利
        buffer.append(winner+":");
        //第四部分为棋盘数据,每个以"-"分割,每行以","分割
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Pieces pieces=board[i][j];
                int type=pieces.type;
                if(pieces.color==2)type+=10;
                buffer.append(type+"-");
            }
            buffer.setCharAt(buffer.length()-1,',');
        }
        buffer.deleteCharAt(buffer.length()-1);
        return buffer.toString();
    }

    //获取谁胜利
    public int isWin() {
        boolean red=false;
        boolean black=false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j].type==5) {
                    if (board[i][j].color==1) black=true;
                    if (board[i][j].color==2) red=true;
                }
            }
        }
        if(red==false)return 1; //红不存在,黑胜利
        if(black==false)return 2; //黑不存在,红胜利
        return 0;
    }

    //移动棋子并进行选手交换
    public boolean moveChess(int who,String data) {
        if (who!=whoChess)return false;
        if (isWin()!=0) return false;
        String[] split = data.split(",");
        if(split.length!=4)return false;
        int beginX=Integer.parseInt(split[0]);
        int beginY=Integer.parseInt(split[1]);
        int endX=Integer.parseInt(split[2]);
        int endY=Integer.parseInt(split[3]);
        //检查是否可以下棋,能下棋就下棋,不能下棋就返回false
        if(canChess(beginX, beginY, endX, endY)){
            //下棋并选手轮替
            if(whoChess==1 || whoChess==2) {
                if(whoChess!=board[beginX][beginY].color)return false;
                board[endX][endY].setTypeColor(board[beginX][beginY]);
                board[beginX][beginY].setTypeColor(0, 0);
                whoChess=whoChess==1?2:1;
                if (isWin()==1)winner="黑";
                if (isWin()==2)winner="红";
            }
            lastPoint=data;
            return true;
        }
        return false;
    }

    //能否下棋,成功返回true,失败返回false
    public boolean canChess(int beginX,int beginY,int endX,int endY) {
        //通用规则:
        //坐标超出范围的问题
        if (beginX < 0 || beginX > board.length-1) return false;
        if (beginY < 0 || beginY > board[0].length-1) return false;
        if (endX < 0 || endX > board.length-1) return false;
        if (endY < 0 || endY > board[0].length-1) return false;
        if (beginX==endX && beginY ==endY) return false;//新位置和旧位置相同
        if (board[beginX][beginY].color==board[endX][endY].color) return false; //新位置和旧位置颜色相同
        //不同棋子不同规则
        int type = board[beginX][beginY].type;
        switch (type) {
            case 1:return can_che(beginX, beginY, endX, endY);
            case 2:return can_ma(beginX, beginY, endX, endY);
            case 3:return can_xiang(beginX, beginY, endX, endY);
            case 4:return can_shi(beginX, beginY, endX, endY);
            case 5:return can_jiang(beginX, beginY, endX, endY);
            case 6:return can_pao(beginX, beginY, endX, endY);
            case 7:return can_bing(beginX, beginY, endX, endY);
            default:break;
        }
        return false;
    }

    //车:走直线,中间不能有间隔
    private boolean can_che(int beginX,int beginY,int endX,int endY) {
        int begin=0,end=0;
        int point=0;
        if (beginX==endX) {
            begin = beginY;
            end = endY;
            point = 1;
        }else if(beginY==endY){
            begin = beginX;
            end = endX;
            point = 2;
        }
        if((point==1||point==2)) {
            if(begin<end) {
                int temp = begin;
                begin = end;
                end =temp;
            }
            if(point==1) {
                for (int i = begin-1; i > end; i--) {
                    if (board[beginX][i].color!=0) return false;
                }
            }else if(point==2) {
                for (int i = begin-1; i > end; i--) {
                    if (board[i][beginY].color!=0) return false;
                }
            }
            return true;
        }
        return false;
    }

    //马:走日,注意卡马脚
    private boolean can_ma(int beginX,int beginY,int endX,int endY) {
        if (beginX==endX+1 || beginX==endX-1) {
            if (beginY==endY+2 && board[beginX][beginY-1].color==0) return true;
            if (beginY==endY-2 && board[beginX][beginY+1].color==0) return true;
        }
        if(beginX==endX+2 && beginY==endY+1 && board[beginX-1][beginY].color==0)return true;
        if(beginX==endX+2 && beginY==endY-1 && board[beginX-1][beginY].color==0)return true;
        if(beginX==endX-2 && beginY==endY+1 && board[beginX+1][beginY].color==0)return true;
        if(beginX==endX-2 && beginY==endY-1 && board[beginX+1][beginY].color==0)return true;
        return false;
    }

    //象:走田,注意卡象脚
    private boolean can_xiang(int beginX,int beginY,int endX,int endY) {
        int color =board[beginX][beginY].color;
        if (color == 1 && endY>4) return false;
        if (color == 2 && endY<5) return false;
        //象的走法有4种,且不能卡象脚
        if (beginX+2 == endX && beginY+2 ==endY && board[beginX+1][beginY+1].color==0) return true;
        if (beginX+2 == endX && beginY-2 ==endY && board[beginX+1][beginY-1].color==0) return true;
        if (beginX-2 == endX && beginY+2 ==endY && board[beginX-1][beginY+1].color==0) return true;
        if (beginX-2 == endX && beginY-2 ==endY && board[beginX-1][beginY-1].color==0) return true;
        return false;
    }

    //士:走斜
    private boolean can_shi(int beginX,int beginY,int endX,int endY) {
        if(endX < 3 || endX >5) return false;
        int color=board[beginX][beginY].color;
        if(color == 1 && endY > 2)return false;
        if(color == 2 && endY < 7)return false;
        //士有4种走法
        if(beginX+1 == endX && beginY+1 == endY)return true;
        if(beginX+1 == endX && beginY-1 == endY)return true;
        if(beginX-1 == endX && beginY+1 == endY)return true;
        if(beginX-1 == endX && beginY-1 == endY)return true;
        return false;
    }

    //将:走九宫格,若将面对面,可以击杀对面将
    private boolean can_jiang(int beginX,int beginY,int endX,int endY) {
        if(endX<3 || endX>5) return false;
        //判断将帅脸对脸
        if (board[beginX][beginY].type==board[endX][endY].type && beginX ==endX) {
            //只要中间没有间隔就允许面杀
            int begin=beginY<endY?beginY:endY;
            int end= beginY>endY?beginY:endY;
            for (int i = begin+1; i < end; i++) {
                if (board[beginX][i].type!=0) return false;
            }
            return true;
        }
        int color = board[beginX][beginY].color;
        if (color == 1 && endY>2) return false;
        if (color == 2 && endY<7) return false;
        if (beginX==endX && (beginY==endY+1||beginY==endY-1)) return true;
        if (beginY==endY && (beginX==endX+1||beginX==endX-1)) return true;
        return false;
    }

    //炮:中间无棋子走直线,有棋子隔着一个棋子打另一个棋子
    private boolean can_pao(int beginX,int beginY,int endX,int endY) {
        int begin=0,end=0;
        int point=0;
        if (beginX==endX) {
            begin = beginY;
            end = endY;
            point = 1;
        }else if(beginY==endY){
            begin = beginX;
            end = endX;
            point = 2;
        }
        if((point==1||point==2)) {
            if(begin<end) {
                int temp = begin;
                begin = end;
                end =temp;
            }
            int pieceSize=0;
            if(point==1) {
                for (int i = begin-1; i > end; i--) {
                    if (board[beginX][i].color!=0) pieceSize++;
                }
            }else if(point==2) {
                for (int i = begin-1; i > end; i--) {
                    if (board[i][beginY].color!=0) pieceSize++;
                }
            }
            if(pieceSize==0 && board[endX][endY].type==0) return true;
            if(pieceSize==1 && board[endX][endY].type!=0) return true;
        }
        return false;
    }

    //兵:一次一格过河之前只能前进,过河之后可以前进左右移动
    private boolean can_bing(int beginX,int beginY,int endX,int endY) {
        int color = board[beginX][beginY].color;
        //前进
        if(color==1 && beginX==endX && beginY==endY-1) return true;
        if(color==2 && beginX==endX && beginY==endY+1) return true;
        //过河了
        if((color==1 && beginY>4) || (color==2 && beginY<5)) {
            if(beginY==endY&&(beginX==endX+1 || beginX == endX-1))return true;
        }
        return false;
    }

}


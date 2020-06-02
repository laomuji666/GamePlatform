package com.lmj.gameplatform.model.gamelobby.gobang;

class Gobang {
    //棋盘数据
    private static final int boardXSize=15;
    private static final int boardYSize=15;
    int board[][]=new int[boardXSize][boardYSize];
    //轮到谁下棋,top="黑",bottom="白",不合条件不许下
    private String whoChess;
    //胜利者的颜色,0无,1黑,2白
    private int winner;

    //重新开始游戏
    public void rePlay() {
        for(int i=0;i<boardXSize;i++){
            for (int j = 0; j < boardYSize; j++) {
                board[i][j]=0;
            }
        }
        whoChess="黑";
        winner=0;
    }

    //检查是否可以下棋
    public boolean checkChess(String position,String data){
        String who="黑";
        if (position.equals("bottom")) who="白";
        if (whoChess.equals(who)) {
            String[]list=data.split(",");
            if (list.length==2){
                int x=Integer.parseInt(list[0]);
                int y=Integer.parseInt(list[1]);
                return canChess(x,y);
            }
        }
        return false;
    }

    //是否允许下棋,允许就执行,不允许返回false
    private boolean canChess(int x,int y){
        //如果游戏已经结束,不允许下
        if (winner!=0)return false;
        //小于棋盘X,Y的个数且大于-1
        if(x<boardXSize && y<boardYSize && x>-1 && y>-1) {
            //如果当前位置已经有棋子了,则不允许再下
            if (board[x][y]!=0) return false;
            //下棋
            int key=1;//默认是黑
            if (whoChess.equals("白")) key=2;
            board[x][y]=key;
            //轮替选手
            if (key==1) whoChess="白";
            if (key==2) whoChess="黑";
            //检查胜利
            winner=checkWin();
            return true;
        }
        return false;
    }

    //返回胜利的颜色
    public int checkWin(){
        boolean isWin=false;
        for (int i=0;i<boardXSize;i++) {
            for (int j=0;j<boardYSize;j++) {
                int color=board[i][j];
                if(color==0)continue;
                //横向
                if(i<boardXSize-4){
                    if(board[i+1][j]==color &&board[i+2][j]==color &&board[i+3][j]==color &&board[i+4][j]==color)
                        isWin=true;
                }
                //纵向
                if(j<boardYSize-4){
                    if(board[i][j+1]==color &&board[i][j+2]==color &&board[i][j+3]==color &&board[i][j+4]==color)
                        isWin=true;
                }
                //斜向,左上到右下
                if(i<boardXSize-4 && j<boardYSize-4){
                    if(board[i+1][j+1]==color &&board[i+2][j+2]==color &&board[i+3][j+3]==color &&board[i+4][j+4]==color)
                        isWin=true;
                }
                //斜向,右上到左下
                if(i-4>=0 && j+4<=boardYSize){
                    if(board[i-1][j+1]==color &&board[i-2][j+2]==color &&board[i-3][j+3]==color &&board[i-4][j+4]==color)
                        isWin=true;
                }
                //如果胜利就跳出循环,返回颜色
                if(isWin)return color;
            }
        }
        return 0;
    }

    //获取棋盘数据
    public String getGameData(){
        StringBuffer buffer=new StringBuffer();
        //第一位表示轮到谁下棋
        buffer.append(whoChess+":");
        //第二位表示谁胜利
        buffer.append(winner+":");
        //每个以-分割,每行以,分割
        for (int i=0;i<boardXSize;i++){
            for (int j = 0; j < boardYSize; j++) {
                buffer.append((board[i][j]+"-"));
            }
            buffer.setCharAt(buffer.length()-1,',');
        }
        buffer.deleteCharAt(buffer.length()-1);
        return buffer.toString();
    }
}

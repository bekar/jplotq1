import java.util.ArrayList;

public class Data {
    public ArrayList<Integer> x = new ArrayList<Integer>();
    public ArrayList<Integer> y = new ArrayList<Integer>();

    private ArrayList<Observer> observer_lst = new ArrayList<Observer>();

    // public Data() {
    // 	this.x = null;
    // 	this.y = null;
    // }

    public void add(Integer _x, Integer _y) {
	x.add(_x);
	y.add(_y);
	this.trigger();
    }

    //reset data to blank
    public void clear(){
	x.clear();
	y.clear();
	this.trigger();
    }

    public void removeObserver(Observer observer) {
	observer_lst.remove(observer);
    }

    public void addObserver(Observer observer) {
	observer_lst.add(observer);
    }

    public void trigger(){
	for(int i = 0; i < observer_lst.size(); i++){
	    observer_lst.get(i).dataUpdate(this);
	}
    }
}

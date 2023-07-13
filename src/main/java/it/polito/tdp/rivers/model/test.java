package it.polito.tdp.rivers.model;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model m = new Model();
		River r = m.getFiumi().get(0);
		m.listaFlussi(r);
		Simulator sim = new Simulator(0.5, r);
		sim.run();
	}

}

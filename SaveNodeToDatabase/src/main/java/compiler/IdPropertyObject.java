package compiler;

public class IdPropertyObject {
	public static int ID_PROP = 30;


	private long id;

	public IdPropertyObject(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String toString() {
		return Long.toString(id);
	}
}
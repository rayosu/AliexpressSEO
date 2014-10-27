package cc.surui.model;import javafx.beans.property.LongProperty;import javafx.beans.property.SimpleLongProperty;import javafx.beans.property.SimpleStringProperty;import javafx.beans.property.StringProperty;public class Product extends Persistent<Product>{	private LongProperty id = new SimpleLongProperty();	private StringProperty name = new SimpleStringProperty();	private StringProperty storeId = new SimpleStringProperty();	private StringProperty url = new SimpleStringProperty();	private StringProperty pic = new SimpleStringProperty();//	private List<String> keywords;	public Product(Long _id, String _name) {		this.setId(_id);		this.setName(_name);	}	public Product(String _storeId, Long _id, String _name, String _url, String _pic) {		this.setStoreId(_storeId);		this.setId(_id);		this.setName(_name);		this.setUrl(_url);		this.setPic(_pic);	}	public Product() {		// TODO Auto-generated constructor stub	}	public Long getId() {		return id.get();	}	public void setId(Long id) {		this.id.set(id);	}	public String getName() {		return name.get();	}	public void setName(String name) {		this.name.set(name);	}	public String getStoreId() {		return storeId.get();	}	public void setStoreId(String storeId) {		this.storeId.set(storeId);	}	public String getUrl() {		return url.get();	}	public void setUrl(String url) {		this.url.set(url);	}	public String getPic() {		return pic.get();	}	public void setPic(String pic) {		this.pic.set(pic);	}//	public List<String> getKeywords() {//		return keywords;//	}////	public void setKeywords(List<String> keywords) {//		this.keywords = keywords;//	}//	@Override//	public Product get(){//		Product product = null;//		List<Map<String, String>> list = SQLiteJDBC.instance().query("SELECT * FROM "+this.getClass().getSimpleName().toUpperCase()//				+" WHERE ID = ?", this.getId());//		if(!list.isEmpty()){//			product = new Product();//			Map<String, String> row = list.get(0);//			product.setId(this.getId());//			product.setName(row.get("NAME"));//			product.setStoreId(row.get("STORE_ID"));//			product.setUrl(row.get("URL"));//			product.setPic(row.get("PIC"));//		}//		return product;//	}	//	@Override//	public boolean update(){//		StringBuilder sql = new StringBuilder("UPDATE "+this.getClass().getSimpleName().toUpperCase()+" SET ")//		.append("NAME = ?, ")//		.append("STORE_ID = ?, ")//		.append("URL = ?, ")//		.append("PIC = ? ")//		.append("WHERE ID = ?");//		int count = SQLiteJDBC.instance().update(sql.toString(),//				this.getName(), this.getStoreId(), this.getUrl(), this.getPic(), this.getId());//		if(count != 0){//			return true;//		}//		return false;//	}}
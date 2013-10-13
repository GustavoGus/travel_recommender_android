/**
 * 
 */
package adm.Repository;

/**
 * @author Julian
 *
 */
public class SearchClass {
//PRIMARY KEY, User_Id INTEGER, Search_Type TEXT, Searching_Word TEXT
	private int userId;
	private String searchType;
	private String searchingWord;
	/**
	 * @param userId
	 * @param searchType
	 * @param searchingWord
	 */
	public SearchClass(int userId, String searchType, String searchingWord) {
		super();
		this.userId = userId;
		this.searchType = searchType;
		this.searchingWord = searchingWord;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchingWord() {
		return searchingWord;
	}
	public void setSearchingWord(String searchingWord) {
		this.searchingWord = searchingWord;
	}
	
}

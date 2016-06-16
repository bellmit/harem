import com.yimayhd.user.client.enums.UserOptions;

public class a {

	public static void main(String[] args) {
		long option = 0;
		boolean isTalentA = UserOptions.CERTIFICATED.has(option);
		boolean isTalentB = UserOptions.USER_TALENT.has(option);
		boolean isMerchant = UserOptions.COMMERCIAL_TENANT.has(option);
		
		System.err.println("isTalentA="+isTalentA);
		System.err.println("isTalentB="+isTalentB);
		System.err.println("isMerchant="+isMerchant);
	}

}

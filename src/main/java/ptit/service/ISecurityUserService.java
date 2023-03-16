package ptit.service;

public interface ISecurityUserService {
	String validatePasswordResetToken(String token);
}

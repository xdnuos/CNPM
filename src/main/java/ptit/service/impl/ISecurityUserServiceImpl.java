package ptit.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptit.entity.PasswordResetToken;
import ptit.repository.PasswordResetTokenRepository;
import ptit.service.ISecurityUserService;

@Service
public class ISecurityUserServiceImpl implements ISecurityUserService {
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;
    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if ((passToken == null)) {
            return "invalidToken";
        }
        final Calendar cal = Calendar.getInstance();
//        System.out.print("aaaaaaaaaaaaaa"+(passToken.getExpiryDate().getTime() - cal.getTime().getTime()));
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "expiredToken";
        }

        return "ok";
    }
}
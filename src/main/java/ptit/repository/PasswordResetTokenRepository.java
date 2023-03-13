package ptit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ptit.entity.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}

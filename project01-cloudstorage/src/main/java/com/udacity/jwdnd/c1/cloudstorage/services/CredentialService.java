package com.udacity.jwdnd.c1.cloudstorage.services;

import com.udacity.jwdnd.c1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.c1.cloudstorage.model.Credential;
import com.udacity.jwdnd.c1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public Credential getCredentialById(Integer credentialId) {
        return this.credentialMapper.getCredentialById(credentialId);
    }

    public boolean existSameUserName(String username, Integer userId) {
        return credentialMapper.getCredentialByUserAndOwner(username, userId) == null;
    }

    public List<Credential> getCredentials(Integer userId) {
        List<Credential> credentials =  this.credentialMapper.getCredentials(userId);
        for (Credential c: credentials
             ) {
            String encryptedPassword = c.getPassword();
            String encodedKey = c.getKey();
            c.setDecryptedPassword(encryptionService.decryptValue(encryptedPassword, encodedKey));
        }
        return credentials;
    }

    public int addCredential(CredentialForm credentialForm) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedSalt);

        return this.credentialMapper.addCredential(
                new Credential(null, credentialForm.getUrl(), credentialForm.getUserName(),
                        encodedSalt,encryptedPassword, credentialForm.getUserId())
        );
    }

    public int updateCredential(CredentialForm credentialForm) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedSalt);

        return this.credentialMapper.updateCredential(
                new Credential(credentialForm.getCredentialId(), credentialForm.getUrl(), credentialForm.getUserName(),
                        encodedSalt,encryptedPassword, credentialForm.getUserId())
        );
    }

    public int deleteCredential(Integer credentialId) {
        return this.credentialMapper.deleteCredential(credentialId);
    }

}
package io.cloudslang.content.ldap.services.users;

import io.cloudslang.content.ldap.entities.CreateUserInput;
import io.cloudslang.content.ldap.utils.LDAPQuery;
import io.cloudslang.content.ldap.utils.MySSLSocketFactory;
import io.cloudslang.content.ldap.utils.ResultUtils;

import javax.naming.CompositeName;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static io.cloudslang.content.constants.OutputNames.*;
import static io.cloudslang.content.ldap.constants.OutputNames.RESULT_COMPUTER_DN;
import static io.cloudslang.content.ldap.utils.ResultUtils.replaceInvalidXMLCharacters;

public class CreateUserService {

    public Map<String, String> execute(CreateUserInput input) {

        Map<String, String> results = ResultUtils.createNewEmptyMap();

        try {
            LDAPQuery ldap = new LDAPQuery();
            String OU = input.getOU();
            String sAMAccountName = input.getSAMAccountName();
            String userCN = input.getUserCommonName();
            String host = input.getHost();
            String username = input.getUsername();
            String password = input.getPassword();
            String keyStore = input.getKeyStore();
            String keyStorePassword = input.getKeyStorePassword();
            String trustStore = input.getTrustKeystore();
            String trustStorePassword = input.getKeyStorePassword();

            // if sAMAccountName not provided assign it from compCN
            if (input.getSAMAccountName().equalsIgnoreCase("")) {
                sAMAccountName = userCN; }

            sAMAccountName = ldap.replaceIllegalCharactersForSAM(sAMAccountName);

            if (input.getEscapeChars()) {
                OU = ldap.normalizeDN(OU, false);
                userCN = ldap.normalizeADExpression(userCN, false);
            }

            Name ou = new CompositeName().add(OU);
            Name user = new CompositeName().add("CN=" + userCN);

            DirContext ctx;

            if (input.getUseSSL()) {
                if (input.getTrustAllRoots()) {
                    ctx = ldap.MakeDummySSLLDAPConnection(host, username, password);
                } else {
                    ctx = ldap.MakeSSLLDAPConnection(host, username, password, "false", keyStore,
                            keyStorePassword, trustStore, trustStorePassword);
                }

            } else {
                ctx = ldap.MakeLDAPConnection(host, username, password);
            }

            DirContext ctxOU = (DirContext) ctx.lookup(ou);

            String value = "\"" + input.getUserPassword() + "\"";
            byte[] bytesPsw = value.getBytes(StandardCharsets.UTF_16LE);

            Attributes userAttrs = new BasicAttributes(true);
            userAttrs.put("objectclass", "user");
            userAttrs.put("ou", OU);
            userAttrs.put("sAMAccountName", sAMAccountName);
            userAttrs.put("unicodePwd", bytesPsw);

            ctxOU.createSubcontext(user, userAttrs);

            Name userDN = new CompositeName().add("CN=" + userCN + "," + OU);
            // enable user account
            Attributes attrs = ctx.getAttributes(userDN, new String[]{"userAccountControl"});
            Attribute attr = attrs.get("userAccountControl");
            int valOld = Integer.parseInt((String) attr.get(0));
            int valNew = valOld & ~0x0002;
            //Specify the changes to make
            ModificationItem[] mods = new ModificationItem[1];
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                    new BasicAttribute("userAccountControl", Integer.toString(valNew)));
            // Perform requested modifications on named object
            ctx.modifyAttributes(userDN, mods);
            ctxOU.close();
            ctx.close();

            results.put(RETURN_RESULT, "Added user account with CN=" + userCN);
            results.put(RESULT_COMPUTER_DN, userDN.toString());
            results.put(RETURN_CODE, "0");

        } catch (NamingException e) {
            Exception exception = MySSLSocketFactory.getException();
            if (exception == null)
                exception = e;
            results.put(EXCEPTION, String.valueOf(exception));
            results.put(RETURN_RESULT, replaceInvalidXMLCharacters(exception.getMessage()));
            results.put(RETURN_CODE, "-1");
        }

        return results;
    }
}

package keep.login;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepClientSBean implements IKeepClientSBean, IKeepClientRemoteSBean {

}

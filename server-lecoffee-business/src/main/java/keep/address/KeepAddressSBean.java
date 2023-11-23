package keep.address;

import java.util.List;

import abstracts.AbstractKeep;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.Query;
import model.Address;
import model.Client;
import to.address.TOAddress;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeepAddressSBean extends AbstractKeep<Address, TOAddress> implements IKeepAddressSBean, IKeepAddressSBeanRemote {

	public KeepAddressSBean() {
		super(Address.class, TOAddress.class);
	}

	@Override
	public void save(TOAddress address) {
		Address model = this.convertToModel(address);
		
		this.getEntityManager().persist(model);
	}

	@Override
	public void remove(TOAddress address) {		
		Address model = this.convertToModel(address);
		
		this.getEntityManager().remove(this.getEntityManager().contains(model) ? model : this.getEntityManager().merge(model));
	}

	@Override
	public void change(TOAddress address) {
		Address model = this.convertToModel(address);
		
		this.getEntityManager().merge(model);
	}

	@Override
	public TOAddress findById(int id) {
		Address address = this.getEntityManager().find(Address.class, id);
		
		return address != null ? this.convertToDTO(address) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOAddress> getAdressesByClientId(int id) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT A FROM ")
			.append(Client.class.getSimpleName()).append(" C ")
			.append(" JOIN C.addresses A ")
			.append(" WHERE C.id = :clientId ");
		
		Query query = this.getEntityManager().createQuery(sql.toString(), Address.class);
		query.setParameter("clientId", id);

		return this.convertModelResults(query.getResultList());
	}

}

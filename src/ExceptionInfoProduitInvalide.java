
/**
 * Classe d'Exception levee lorsqu'on tente de modifier un produit avec une
 * information invalide.
 * @author melanie lord
 * @version E25
 */
public class ExceptionInfoProduitInvalide extends RuntimeException {
   public ExceptionInfoProduitInvalide (String msgErr) {
      super(msgErr);
   }
}

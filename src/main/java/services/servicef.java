package services;

import java.util.List;

public interface servicef <S>{
    public void ajouterSession(S s);
    public void UpdateSession(S s);
    public void DeleteSession(S s);
    public List<S>AfficherSession();
}

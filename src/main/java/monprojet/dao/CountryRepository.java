package monprojet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import monprojet.entity.City;
import monprojet.entity.Country;

// This will be AUTO IMPLEMENTED by Spring 
//

public interface CountryRepository extends JpaRepository<Country, Integer> {
    /**
     * Une méthode qui, pour un pays dont l'ID est passé en paramètre, calcule sa population (somme des populations des villes)
     * @param idCountry
     * @return La population au ID fourni, null si pas trouvé.
     */
    @Query(value = "SELECT SUM(population) "
        +   "FROM City "
        +   "INNER JOIN Country ON City.country_id = Country.id "
        +   "WHERE Country.id = :idCountry",
        nativeQuery = true)
    public int calculPopulationCountry(int idCountry);

    public static final int population = 0;

    public interface NamePopulationCountry{
        String getName();
        int getPopulation();
    }

    /**
     * Une méthode sans paramètre, qui renvoie une liste (nom du pays, population)
     * @return La list des pays, null si pas trouvé.
     */
    @Query(value = "SELECT Country.name, SUM(City.population) "
        +   "FROM City "
        +   "INNER JOIN Country ON City.country_id = Country.id "
        +   "GROUP BY Country.name",
        nativeQuery = true)
    public List<NamePopulationCountry> listCountry();
}

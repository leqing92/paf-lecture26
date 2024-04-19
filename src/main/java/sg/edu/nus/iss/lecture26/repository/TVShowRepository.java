package sg.edu.nus.iss.lecture26.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.lecture26.Constant;

@Repository
public class TVShowRepository {
    
    @Autowired
    private MongoTemplate template;
// anything in find is criteria
// anything outside find is query

    /*
     * db.tvshows.find({
     *  name: { $regex: 'name', $options: 'i'},
     *  genres : { $all: ['Drama', 'Thriller']}     * 
     * })
     * .projection ({ name : 1, genres : 1})
     * .sort({ name : -1})
     * .limit(5)
     */
    public List <Document> findShowByName (String name){
        // create filter
        // db.tvshows.find({name: 'name'})
        // Criteria criteria = Criteria.where(Constant.F_NAME)
        //                             .is(name);

        /*
        * db.tvshows.find({
        *  name: { $regex: 'name', $options: 'i'}
        * })
        */
        // Criteria criteria = Criteria.where(Constant.F_NAME)                                    
        //                             .regex(name, "i");   

        Criteria criteria = Criteria.where(Constant.F_NAME).regex(name, "i")
                                    .and(Constant.F_GENRES).all("Drama", "Thriller");

        //create query with filter and sort and limit
        Query query = Query.query(criteria)
                        .with(Sort.by(Direction.DESC, Constant.F_NAME))
                        .limit(5);

        //projection  {(name : 1, genres : 1)}
        query.fields().include(Constant.F_NAME, Constant.F_GENRES);

        List <Document> results = template.find(query, Document.class, Constant.C_TVSHOWS);

        return results;
    }

    /*
     * db.tvshows.find({
     *  language : { $regex: 'language', $options : 'i'}
     * })
     */
    public Long countShowsByLanguage (String lang){
        Criteria criteria = Criteria.where(Constant.F_LANGUAGE).regex(lang, "i");

        Query query = Query.query(criteria);

        return template.count(query, Constant.C_TVSHOWS);
    }

    /*
     * db.tvshows.distinct("type", {"rating.average": {$gte : 7}})
     */
    public List <String> getTypesByRating (float rating){
        Criteria criteria = Criteria.where(Constant.F_AVERATE_RATING).gte(rating);

        Query query = Query.query(criteria);

        return template.findDistinct(query, Constant.F_TYPE, Constant.C_TVSHOWS, String.class);
    }
}

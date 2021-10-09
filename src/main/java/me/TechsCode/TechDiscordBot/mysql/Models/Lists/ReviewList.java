package me.TechsCode.TechDiscordBot.mysql.Models.Lists;

import me.TechsCode.TechDiscordBot.mysql.Models.DbMarket;
import me.TechsCode.TechDiscordBot.mysql.Models.DbMember;
import me.TechsCode.TechDiscordBot.mysql.Models.Resource;
import me.TechsCode.TechDiscordBot.mysql.Models.Review;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ReviewList extends ArrayList<Review> {

    public ReviewList(int initialCapacity) {
        super(initialCapacity);
    }

    public ReviewList() {}

    public ReviewList(Collection<? extends Review> c) {
        super(c);
    }

    public ReviewList id(int id){
        return stream().filter(review -> review.getId() == id).collect(Collectors.toCollection(ReviewList::new));
    }

    public ReviewList memberId(int memberId){
        return stream().filter(review -> review.getMemberId() == memberId).collect(Collectors.toCollection(ReviewList::new));
    }

    public ReviewList member(DbMember member){
        return stream().filter(review -> review.getMember().equals(member)).collect(Collectors.toCollection(ReviewList::new));
    }

    public ReviewList resourceId(int resourceId){
        return stream().filter(review -> review.getResourceId() == resourceId).collect(Collectors.toCollection(ReviewList::new));
    }

    public ReviewList resource(Resource resource){
        return stream().filter(review -> review.getResource().equals(resource)).collect(Collectors.toCollection(ReviewList::new));
    }

    public ReviewList dateEquels(long date){
        return stream().filter(review -> review.getDate() == date).collect(Collectors.toCollection(ReviewList::new));
    }

    public ReviewList dateGreater(long date){
        return stream().filter(review -> review.getDate() >= date).collect(Collectors.toCollection(ReviewList::new));
    }

    public ReviewList dateLess(long date){
        return stream().filter(review -> review.getDate() <= date).collect(Collectors.toCollection(ReviewList::new));
    }

}
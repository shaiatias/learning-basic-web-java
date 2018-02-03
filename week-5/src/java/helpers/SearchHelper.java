//package helpers;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//import model.Contact;
//
//public class SearchHelper {
//
//    static public List<Contact> doSearch(String currentUsername, String name, boolean nameExact, String tel, boolean telExact, String operator) {
//
//        if (name.equals("") && tel.equals("")) {
//            return Arrays.asList();
//        }
//
//        List<Contact> fromRepository = Arrays.asList(new Contact("shai"), new Contact("ben"))
//                .stream()
//                .filter(contact -> currentUsername.equals("") ? contact.isPublic : true)
//                .filter(contact -> !currentUsername.equals("") && (contact.isPublic || currentUsername.equals(contact.createdByUser)))
//                .collect(Collectors.toList());
//        
//        if (operator.equalsIgnoreCase("and")) {
//
//            return fromRepository
//                    .stream()
//                    .filter(contact -> !name.equals("") && nameExact ? contact.name.equalsIgnoreCase(name) : true)
//                    .filter(contact -> !name.equals("") && !nameExact ? contact.name.contains(name) : true)
//                    .filter(contact -> !tel.equals("") && telExact ? contact.tel.equalsIgnoreCase(tel) : true)
//                    .filter(contact -> !tel.equals("") && !telExact ? contact.tel.contains(tel) : true)
//                    .collect(Collectors.toList());
//
//        } else {
//
//            Set<Contact> collect1 = fromRepository
//                    .stream()
//                    .filter(contact -> name.equals("") ? false : true)
//                    .filter(contact -> !name.equals("") && nameExact ? contact.name.equalsIgnoreCase(name) : true)
//                    .filter(contact -> !name.equals("") && !nameExact ? contact.name.contains(name) : true)
//                    .collect(Collectors.toSet());
//
//            Set<Contact> collect2 = fromRepository
//                    .stream()
//                    .filter(contact -> tel.equals("") ? false : true)
//                    .filter(contact -> !tel.equals("") && telExact ? contact.tel.equalsIgnoreCase(tel) : true)
//                    .filter(contact -> !tel.equals("") && !telExact ? contact.tel.contains(tel) : true)
//                    .collect(Collectors.toSet());
//            
//            collect1.addAll(collect2);
//            
//            return new ArrayList(collect1);
//        }
//    }
//}

package nl.knaw.dans.dataverse;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by ben on 04-03-16.
 */
public class RuleExecutionSetTest {

    private static final String ATTR_NAME_EMAIL = "mail";
    private static final String ATTR_NAME_SURNAME = "sn";
    private static final String ATTR_NAME_PREFIX = "prefix";
    private static final String ATTR_NAME_GIVENNAME = "givenName";
    private static final String ATTR_NAME_ROLE = "eduPersonAffiliation";
    public static final String ATTR_NAME_ORG = "schacHomeOrganization";
    private static final String ATTR_NAME_PRINCIPAL = "eduPersonPrincipalName";
    public static final String ATTR_NAME_ENTITLEMENT = "entitlement";
    private static final String CREATOR = "urn:x-surfnet:dans.knaw.nl:dataversenl:role:dataset-creator";

    private static List<Rule> allRules;
    private static RuleExecutionSet res;
    private static TestRuleService trs;

    @Before
    public void setUp() {
        trs = new TestRuleService();
        res = new RuleExecutionSet(trs, null, null);
        allRules = trs.findAll();
    }

    @Test
    public void testMatchNoRules() throws Exception {
        Map<String, String> dans = createUserProps("Companjen","Ben",null,"ben.companjen@dans.knaw.nl","dans.knaw.nl",null,"staff");
        assertTrue("No rules should match for DANS", res.getMatchedRuleCondition(dans,trs.findRulesByOrgName("dans.knaw.nl")).isEmpty());
    }

    @Test
    public void testGetMatchingRules() throws Exception {
        Map<String,String> pthu = createUserProps("User","Test","de","test.user@pthu.nl","vu.nl",CREATOR,"employee");

        List<Rule> matchingRules = res.getMatchedRuleCondition(pthu, allRules);
        //assertTrue("PThU email matches", );

    }

    private static Map<String, String> createUserProps(String lastName, String givenName, String prefix, String email,
                                                       String orgName, String entitlement, String affiliation) {
        Map<String, String> props = new HashMap<String, String>();
        props.put(ATTR_NAME_SURNAME, lastName);
        props.put(ATTR_NAME_GIVENNAME, givenName);
        props.put(ATTR_NAME_PREFIX, prefix);
        props.put(ATTR_NAME_EMAIL, email);
        props.put(ATTR_NAME_ORG, orgName);
        props.put(ATTR_NAME_ROLE, affiliation);
        props.put(ATTR_NAME_ENTITLEMENT, entitlement);

        return props;
    }

    static class TestRuleService implements RuleServiceLocal {
        List<Rule> allRules;

        public TestRuleService() {
            allRules = new ArrayList<Rule>();
            addRule(createRule("vu.nl", "all VU users"));
            addRule(createRule("vu.nl", "entitled VU users"));
            addRule(createRule("vu.nl", "entitled PThU users"));

        }

        private void addRule(Rule rule) {
            allRules.add(rule);
        }

        private Rule createRule(String orgName, String description) {
            Rule rule = new Rule();
            rule.setOrgName(orgName);
            rule.setDescription(description);
            return rule;
        }

        @Override
        public List<Rule> findAll() {
            return allRules;
        }

        @Override
        public Rule findRuleById(Long id) {
            return allRules.get(id.intValue());
        }

        @Override
        public List<Rule> findRulesByOrgName(String orgName) {
            List<Rule> matching = new ArrayList<Rule>();
            for (Rule r : allRules) {
                if (r.getOrgName().equalsIgnoreCase(orgName)) {
                    matching.add(r);
                }
            }

            return matching;
        }

        @Override
        public Rule findRuleByCondition(RuleCondition rc) {
            return null;
        }


    }
}
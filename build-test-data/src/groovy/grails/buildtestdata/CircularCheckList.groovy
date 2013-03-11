package grails.buildtestdata

public class CircularCheckList {

    Map checkList = [:]

    // TODO: look to see if we still need to force, if that adds anything
    def update(domain, force = false) {
        if (force || !checkList[domain.class.name]) {
            checkList[domain.class.name] = domain // should short circuit circular references
        }
    }

    def retrieve(domainArtefact) {
        if(!domainArtefact) {
            return null
        }
        def object = checkList[domainArtefact.clazz.name]
        def itr = domainArtefact.subClasses.iterator()
        while(!object && itr.hasNext()) {
            object = retrieve(itr.next())
        }

        return object
    }
}
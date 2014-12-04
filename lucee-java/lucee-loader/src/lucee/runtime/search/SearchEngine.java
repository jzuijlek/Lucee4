/**
 *
 * Copyright (c) 2014, the Railo Company LLC. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package lucee.runtime.search;

import java.io.IOException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import lucee.commons.io.res.Resource;
import lucee.runtime.config.Config;
import lucee.runtime.type.Query;
import lucee.commons.io.log.LogAndSource;

/**
 * interface for a Search Engine
 */
public interface SearchEngine {

    /**
     * overwrite allowed
     */
    public static final boolean ALLOW_OVERWRITE = true;

    /**
     * overwrite denied
     */
    public static final boolean DENY_OVERWRITE = false;

    /**
     * constructor of the class
     * @param config 
     * @param searchDir directory where the lucee xml file is
     * @param log 
     * @throws IOException
     * @throws SAXException
     * @throws SearchException
     */
    public abstract void init(Config config, Resource searchDir, LogAndSource log) // FUTURE remove argument LogAndSource
            throws SAXException, IOException, SearchException;

    /**
     * returns a collection by name
     * @param name name of the desired collection (case insensitive)
     * @return returns lucene collection object matching name
     * @throws SearchException if no matching Collection exist 
     */
    public abstract SearchCollection getCollectionByName(String name)
            throws SearchException;

    /**
     * @return returns all collections as a query object
     */
    public abstract Query getCollectionsAsQuery();

    /**
     * Creates a new Collection and Store it (creating always a spellindex)
     * @param name The Name of the Collection
     * @param path the path to store
     * @param language The language of the collection
     * @param allowOverwrite
     * @return New SearchCollection
     * @throws SearchException
     */
    public abstract SearchCollection createCollection(String name, Resource path,
            String language, boolean allowOverwrite) throws SearchException;


    /**
     * @return returns the directory of the search storage
     */
    public abstract Resource getDirectory();

    /**
     * @return returns the logfile of the search storage
     */
    public abstract LogAndSource getLogger(); // FUTURE deprecated

    /**
     * return XML Element Matching index id
     * @param collElement XML Collection Element
     * @param id
     * @return XML Element
     */
    public abstract Element getIndexElement(Element collElement, String id);

    /**
     * @return returns the Name of the search engine to display in admin
     */
    public abstract String getDisplayName();

}
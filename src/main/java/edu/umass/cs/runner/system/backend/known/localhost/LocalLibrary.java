package edu.umass.cs.runner.system.backend.known.localhost;

import edu.umass.cs.runner.system.backend.AbstractLibrary;
import edu.umass.cs.runner.Runner;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LocalLibrary extends AbstractLibrary {
    public static final int port = 8000;
    public static final String jshome = "src/javascript";

    private List<String> backendHeaders = new ArrayList<String>();

    public LocalLibrary(String propertiesURL) {
        if (propertiesURL == null || propertiesURL.equals(""))
            init();
        else {
            try {
                super.props = new Properties();
                super.props.load(new FileReader(propertiesURL));
            } catch (FileNotFoundException e) {
                Runner.LOGGER.warn(e);
                Runner.LOGGER.info(e.getLocalizedMessage()+"\nUsing default value instead...");
                init();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getActionForm() {
        return "";
    }

    @Override
    public void init() {
        try{
            super.props = new Properties();
            super.props.load(new FileReader(AbstractLibrary.PARAMS));
        }catch(IOException io){
            Runner.LOGGER.fatal(io);
            System.err.println(io.getMessage());
            System.exit(1);
        }
    }

    @Override
    public List<String> getBackendHeaders()
    {
        return backendHeaders;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LocalLibrary) {
            LocalLibrary that = (LocalLibrary) o;
            Set<String> thisBackendHeaders = new HashSet<String>(this.backendHeaders);
            Set<String> thatBackendHeaders = new HashSet<String>(that.backendHeaders);
            if (thisBackendHeaders.equals(thatBackendHeaders))
                return true;
            else {
                LOGGER.debug(String.format("Backend headers not equal (%s vs. %s)",
                        StringUtils.join(thisBackendHeaders, ","),
                        StringUtils.join(thatBackendHeaders, ",")));
                return false;
            }
        } else return false;
    }

}

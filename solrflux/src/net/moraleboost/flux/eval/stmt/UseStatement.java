package net.moraleboost.flux.eval.stmt;

import net.moraleboost.flux.eval.EvalContext;
import net.moraleboost.flux.eval.EvalException;

import org.apache.solr.client.solrj.impl.HttpSolrServer;

public class UseStatement extends BaseStatement
{
    private String url;
    private String name;
    
    public UseStatement()
    {
        super();
    }
    
    public void setUrl(String url)
    {
    	if (url.endsWith("/")){
    		this.name = url.substring(url.lastIndexOf("/", url.length()-2)+1, url.length()-1);
    	}
    	else{
    		this.name = url.substring(url.lastIndexOf("/", url.length()-2)+1, url.length());
    	}
        this.url = url;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }

    public Object execute(EvalContext ctx) throws EvalException
    {
        if (url == null) {
		    throw new EvalException("Url is null.");
		}
		
		HttpSolrServer server = new HttpSolrServer(url);
		if (name == null) {
		    ctx.setDefaultServer(server);
		} else {
		    ctx.putGlobal(name, server);
		}
        
        return null;
    }
}

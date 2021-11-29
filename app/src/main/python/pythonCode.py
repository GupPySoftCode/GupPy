import sys
from os.path import dirname, join
from com.chaquo.python import Python

def main(ScriptFieldText):

    folder_loc = str(Python.getPlatform().getApplication().getFilesDir())
    filenm = join(dirname(folder_loc), 'f.txt')
    
    try:
        mstdout = sys.stdout
        sys.stdout = open(filenm, 'w', encoding = 'utf8', errors = 'ignore')
        exec(ScriptFieldText)
        sys.stdout.close()
        sys.stdout = mstdout
        fvalue = open(filenm, 'r').read()
        
    except Exception as e:
        sys.stdout = mstdout
        fvalue = e
    return str(fvalue)
    
    
